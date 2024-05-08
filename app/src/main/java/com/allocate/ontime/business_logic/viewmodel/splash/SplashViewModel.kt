package com.allocate.ontime.business_logic.viewmodel.splash

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.allocate.ontime.BuildConfig
import com.allocate.ontime.business_logic.api_manager.SyncManagerServiceImpl
import com.allocate.ontime.business_logic.data.DataOrException
import com.allocate.ontime.business_logic.data.room.entities.DeviceInformation
import com.allocate.ontime.business_logic.data.room.entities.EmployeePacket
import com.allocate.ontime.business_logic.data.shared_preferences.SecureSharedPrefs
import com.allocate.ontime.business_logic.repository.DaoRepository
import com.allocate.ontime.business_logic.repository.DeviceInfoRepository
import com.allocate.ontime.business_logic.utils.Constants
import com.allocate.ontime.business_logic.utils.DeviceUtility
import com.allocate.ontime.presentation_logic.model.AppInfo
import com.allocate.ontime.presentation_logic.model.DeviceInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
@SuppressLint("StaticFieldLeak")
class SplashViewModel @Inject constructor(
    private val repository: DeviceInfoRepository,
    private val daoRepository: DaoRepository,
    private val deviceUtility: DeviceUtility,
    private val syncManagerServiceImpl: SyncManagerServiceImpl,
    @ApplicationContext private val context: Context,
) : ViewModel() {

    private val _acknowledgementStatus = MutableStateFlow(0)
    val acknowledgementStatus = _acknowledgementStatus.asStateFlow()

    companion object {
        const val TAG = "SplashViewModel"
    }

    init {
        startApiCall()
    }

    fun chainingApiCalling() {
        viewModelScope.launch(Dispatchers.IO) {
                syncManagerServiceImpl.sync()
        }
    }

    private fun startApiCall() {
        viewModelScope.launch(Dispatchers.IO) {
            val messageApiData = async { repository.postMessage() }.await()
            if (messageApiData.data != null) {
                val edHelper = com.allocate.ontime.encryption.EDHelper
                if (messageApiData.data?.isSuccessful == true) {
                    val data = messageApiData.data!!.body()?.data
                    Log.d(TAG, "Message Data : $data")
                    val decryptedData = edHelper.decrypt(data.toString())
                    Log.d(TAG, "decryptedData of Message : $decryptedData")
                }
            } else {
                Log.d(TAG, "employeeApiData.data is null")
            }


            val deviceInfoApiData = async { repository.getDeviceInfo(context) }.await()
            Log.i(TAG, "deviceInfoApiData : success")

            deviceInfoApiData.data?.responsePacket?.first()?.let { data ->
                SecureSharedPrefs(context).saveData(
                    Constants.AS_API_URL,
                    data.ASApiURL
                )
                SecureSharedPrefs(context).saveData(
                    Constants.DEVICE_ID,
                    data.DeviceId.toString()
                )
                addDeviceInfo(
                    deviceInformation = DeviceInformation(
                        id = data._id,
                        deviceId = data.DeviceId.toLong(),
                        trustOrganization = data.TrustOrganization,
                        uniqueIdentifier = data.Unique_Identifier,
                        location = data.Location,
                        serialNumber = data.SerialNumber,
                        latitude = data.Latitude.toDouble(),
                        longitude = data.Longitude.toDouble(),
                        postcode = data.Postcode,
                        status = data.Status,
                        asInstance = data.ASInstance,
                        asEmployeeOnlineURL = data.ASEmployeeOnlineURL,
                        asApiURL = data.ASApiURL,
                        appVersion = data.AppVersion,
                        locationCode = data.LocationCode,
                        acknowledgementStatus = 0,
                        sites = data.Sites,
                        siteName = data.SiteName,
                        isRLD = data.IsRLD
                    )
                )
            }
            observeAllDeviceInfo(this, deviceInfoApiData)
        }
    }

    private suspend fun observeAllDeviceInfo(
        coroutineScope: CoroutineScope,
        getDeviceInfoApiData: DataOrException<DeviceInfo, Exception>,
    ) {
        daoRepository.getAllDeviceInfo().distinctUntilChanged().collect { listOfDeviceInfo ->
            if (listOfDeviceInfo.isEmpty()) {
                Log.d(TAG, "Empty List")
            } else {
                if (acknowledgementStatus.value == 0) {
                    coroutineScope.async {
                        getDeviceInfoApiData.data?.let {
                            val result = repository.postDeviceInfo(
                                appInfo = AppInfo(
                                    id = it.responsePacket.first()._id,
                                    macAddress = deviceUtility.getMacAddress(context),
                                    app = "",
                                    appVersion = BuildConfig.VERSION_NAME
                                )
                            )
                            if (result.data?.ResponseCode == 201) {
                                _acknowledgementStatus.value = 1
                                getDeviceInfoApiData.data?.responsePacket?.first()?.let { data ->
                                    updateDeviceInfo(
                                        deviceInformation = DeviceInformation(
                                            id = data._id,
                                            deviceId = data.DeviceId.toLong(),
                                            trustOrganization = data.TrustOrganization,
                                            uniqueIdentifier = data.Unique_Identifier,
                                            location = data.Location,
                                            serialNumber = data.SerialNumber,
                                            latitude = data.Latitude.toDouble(),
                                            longitude = data.Longitude.toDouble(),
                                            postcode = data.Postcode,
                                            status = data.Status,
                                            asInstance = data.ASInstance,
                                            asEmployeeOnlineURL = data.ASEmployeeOnlineURL,
                                            asApiURL = data.ASApiURL,
                                            appVersion = data.AppVersion,
                                            locationCode = data.LocationCode,
                                            acknowledgementStatus = acknowledgementStatus.value,
                                            sites = data.Sites,
                                            siteName = data.SiteName,
                                            isRLD = data.IsRLD
                                        )
                                    )
                                }
                            } else {
                                result.e?.let {
                                    Log.e(TAG, "Exception: $it")
                                }
                            }
                        } ?: run {
                            getDeviceInfoApiData.e?.let {
                                Log.e(TAG, "Exception: $it")
                            }
                        }
                    }.await()
                }
            }
        }
    }

    private suspend fun addDeviceInfo(deviceInformation: DeviceInformation) =
        viewModelScope.launch(Dispatchers.IO) {
            async { daoRepository.addDeviceInfo(deviceInformation) }.await()
        }

    private suspend fun updateDeviceInfo(deviceInformation: DeviceInformation) =
        viewModelScope.launch(Dispatchers.IO) {
            async { daoRepository.updateDeviceInfo(deviceInformation) }.await()
        }
}
