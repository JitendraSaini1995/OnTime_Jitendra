package com.allocate.ontime.business_logic.repository

import android.content.Context
import android.util.Log
import com.allocate.ontime.business_logic.data.DataOrException
import com.allocate.ontime.business_logic.data.shared_preferences.SecureSharedPrefs
import com.allocate.ontime.presentation_logic.model.DeviceInfo
import com.allocate.ontime.business_logic.network.DeviceInfoApi
import com.allocate.ontime.business_logic.network.DeviceSettingApi
import com.allocate.ontime.business_logic.network.SiteJobListApi
import com.allocate.ontime.business_logic.network.SuperAdminApi
import com.allocate.ontime.business_logic.utils.Constants
import com.allocate.ontime.business_logic.utils.DeviceUtility
import com.allocate.ontime.encryption.EDModel
import com.allocate.ontime.presentation_logic.model.AppInfo
import com.allocate.ontime.presentation_logic.model.DeviceSettingInfo
import com.allocate.ontime.presentation_logic.model.EditDeviceInfo
import com.allocate.ontime.presentation_logic.model.SiteJobListRequest
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

class DeviceInfoRepository @Inject constructor(
    private val deviceInfoApi: DeviceInfoApi,
    private val superAdminApi: SuperAdminApi,
    private val deviceUtility: DeviceUtility,
    private val deviceSettingApi: DeviceSettingApi,
    private val siteJobListApi: SiteJobListApi,
    private val daoRepository: DaoRepository,
    @ApplicationContext private val context: Context
) {
    companion object {
        const val TAG = "DeviceInfoRepository"
    }

    suspend fun getDeviceInfo(context: Context): DataOrException<DeviceInfo, Exception> {
        val dataOrException = DataOrException<DeviceInfo, Exception>()
        try {
//           dataOrException.data = deviceInfoApi.getDeviceInfo(imei = deviceUtility.getIMEI())
            dataOrException.data = deviceInfoApi.getDeviceInfo(imei = "867584036299027")
        } catch (exception: Exception) {
            dataOrException.e = exception
            Log.e(TAG, "getAllDeviceInfo: ${dataOrException.e}")
        }
        return dataOrException
    }

    suspend fun postDeviceInfo(
        appInfo: AppInfo
    ): DataOrException<EditDeviceInfo, Exception> {
        val dataOrException = DataOrException<EditDeviceInfo, Exception>()
        try {
            dataOrException.data = deviceInfoApi.postEditDeviceInfo(appInfo)
        } catch (exception: Exception) {
            dataOrException.e = exception
            Log.e(TAG, "getAllDeviceInfo: ${dataOrException.e}")
        }
        return dataOrException
    }

    suspend fun postSuperAdminDetails(): DataOrException<Response<EDModel>, Exception> {
        val dataOrException = DataOrException<Response<EDModel>, Exception>()
        try {
            dataOrException.data = superAdminApi.getCISuperAdminDetails()
            Log.i(TAG, "getCISuperAdminDetails success")
        } catch (exception: Exception) {
            dataOrException.e = exception
            Log.e(TAG, "getSuperAdminDetails: ${dataOrException.e}")
        }
        return dataOrException
    }

    suspend fun postDeviceSettingDetails(): DataOrException<Response<EDModel>, Exception> {
        val dataOrException = DataOrException<Response<EDModel>, Exception>()
        val deviceId: String = SecureSharedPrefs(context).getData(
            Constants.DEVICE_ID,
            ""
        )
        val timeStamp: String = SecureSharedPrefs(context).getData(
            Constants.TIME_STAMP,
            "0"
        )
        val deviceSettingInfo = DeviceSettingInfo(timeStamp, deviceId)
        val encryptedDeviceSettingInfo = EDModel("").encryptDeviceInfo(deviceSettingInfo)
        try {
            dataOrException.data =
                deviceSettingApi.getCIDeviceSettingDetails(encryptedDeviceSettingInfo)
            Log.i(TAG, "getCIDeviceSettingDetails : ${dataOrException.data}")
        } catch (exception: Exception) {
            dataOrException.e = exception
            Log.e(TAG, "getCIDeviceSettingDetails: ${dataOrException.e}")
        }
        return dataOrException
    }

    suspend fun postSiteJobList(): DataOrException<Response<EDModel>, Exception> {
        val dataOrException = DataOrException<Response<EDModel>, Exception>()
        val siteJobListRequest = SiteJobListRequest(0,daoRepository.getSiteTimestamp() ?: 0)
        val encryptedSiteJobList = EDModel("").encryptDeviceInfo(siteJobListRequest)
        Log.i(TAG, "encryptedSiteJobList : $encryptedSiteJobList")
        try {
            dataOrException.data =
                siteJobListApi.getSiteJobList(encryptedSiteJobList)
            Log.i(TAG, "getSiteJobList : ${dataOrException.data}")
        } catch (exception: Exception) {
            dataOrException.e = exception
            Log.e(TAG, "getSiteJobList: ${dataOrException.e}")
        }
        return dataOrException
    }

}