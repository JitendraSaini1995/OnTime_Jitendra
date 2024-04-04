package com.allocate.ontime.business_logic.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.allocate.ontime.business_logic.api_worker.WorkChainWorker
import com.allocate.ontime.business_logic.data.shared_preferences.SecureSharedPrefs
import com.allocate.ontime.business_logic.repository.DeviceInfoRepository
import com.allocate.ontime.business_logic.utils.Constants
import com.allocate.ontime.encryption.EDModel
import com.allocate.ontime.presentation_logic.model.DeviceSettingResponse
import com.allocate.ontime.presentation_logic.model.SuperAdminResponse
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
@SuppressLint("StaticFieldLeak")
class MainViewModel @Inject constructor(
    private val repository: DeviceInfoRepository, @ApplicationContext private val context: Context
) : ViewModel() {

    private val TAG = "MainViewModel"

    fun fetchSuperAdminDetails() {
        viewModelScope.launch {
            val result = repository.postSuperAdminDetails()
            Log.d(TAG, "result : $result")
            if (result.data != null) {
                if (result.data?.isSuccessful == true) {
                    val data = result.data?.body()?.data
                    Log.d(TAG, "postSuperAdminDetails data : $data")
                    val decryptedData =
                        EDModel(data.toString()).getResponseModel(SuperAdminResponse::class.java)
                    Log.d(TAG, "decryptedData : $decryptedData")
                    SecureSharedPrefs(context).saveData(
                        Constants.USER_NAME, decryptedData.ResponsePacket.UserName
                    )
                    SecureSharedPrefs(context).saveData(
                        Constants.PASSWORD, decryptedData.ResponsePacket.Password
                    )
                }
            } else {
                Log.d(TAG, "result.data is null")
            }
        }
    }

    fun fetchDeviceSettingDetails() {
        viewModelScope.launch {
            val deviceSettingApiData = async { repository.postDeviceSettingDetails() }.await()
            if (deviceSettingApiData.data != null) {
                val edHelper = com.allocate.ontime.encryption.EDHelper
                if (deviceSettingApiData.data?.isSuccessful == true) {
                    val data = deviceSettingApiData.data!!.body()?.data
                    val decryptedData = edHelper.decrypt(data.toString())
                    val response = Gson().fromJson(decryptedData, DeviceSettingResponse::class.java)
                    Log.d(TAG, "response : $response")
                    val timeStamp = response.ResponsePacket.TimeStamp
                    Log.d(TAG, "timeStamp : $timeStamp")
                    Log.d(TAG, "startApi method called from main viewmodel..")
                    SecureSharedPrefs(context).saveData(
                        Constants.TIME_STAMP, timeStamp.toString()
                    )
                }
            } else {
                Log.d(TAG, "deviceSettingApiData.data is null")
            }
        }
    }

    companion object {
        @SuppressLint("EnqueueWork")
        fun startPeriodicWork(context: Context) {
            val constraints =
                Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()

            val periodicApiChain = PeriodicWorkRequestBuilder<WorkChainWorker>(
                repeatInterval = 15, repeatIntervalTimeUnit = TimeUnit.MINUTES
            ).setConstraints(constraints).build()

            WorkManager.getInstance(context).enqueueUniquePeriodicWork(
                "ApiChainWorker", ExistingPeriodicWorkPolicy.KEEP, periodicApiChain
            )

        }
    }
}