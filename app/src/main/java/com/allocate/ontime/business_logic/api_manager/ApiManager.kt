package com.allocate.ontime.business_logic.api_manager

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.lifecycle.viewModelScope
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.allocate.ontime.business_logic.api_worker.WorkChainWorker
import com.allocate.ontime.business_logic.data.room.DeviceInformation
import com.allocate.ontime.business_logic.data.room.Job
import com.allocate.ontime.business_logic.data.room.Site
import com.allocate.ontime.business_logic.data.shared_preferences.SecureSharedPrefs
import com.allocate.ontime.business_logic.repository.DaoRepository
import com.allocate.ontime.business_logic.repository.DeviceInfoRepository
import com.allocate.ontime.business_logic.utils.Constants
import com.allocate.ontime.business_logic.viewmodel.splash.SplashViewModel
import com.allocate.ontime.encryption.EDModel
import com.allocate.ontime.presentation_logic.model.DeviceSettingResponse
import com.allocate.ontime.presentation_logic.model.SiteJobResponse
import com.allocate.ontime.presentation_logic.model.SuperAdminResponse
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class ApiManager @Inject constructor(
    private val repository: DeviceInfoRepository,
    private val daoRepository: DaoRepository,
    @ApplicationContext private val context: Context,
    private val scope: CoroutineScope
) {

    private val TAG = "ApiManager"

    fun fetchSuperAdminDetails() {
        scope.launch(Dispatchers.IO) {
            val result = async { repository.postSuperAdminDetails() }.await()
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
        scope.launch(Dispatchers.IO) {
            val deviceSettingApiData = async { repository.postDeviceSettingDetails() }.await()
            if (deviceSettingApiData.data != null) {
                val edHelper = com.allocate.ontime.encryption.EDHelper
                if (deviceSettingApiData.data?.isSuccessful == true) {
                    val data = deviceSettingApiData.data!!.body()?.data
                    val decryptedData = edHelper.decrypt(data.toString())
                    val deviceSettingJsonString = Gson().toJson(decryptedData)
                    SecureSharedPrefs(context).saveData(
                        Constants.DEVICE_SETTING_DATA, deviceSettingJsonString
                    )
                    val response = Gson().fromJson(decryptedData, DeviceSettingResponse::class.java)
                    Log.d(TAG, "response : $response")
                    val timeStamp = response.ResponsePacket.TimeStamp
                    Log.d(TAG, "timeStamp : $timeStamp")
                    SecureSharedPrefs(context).saveData(
                        Constants.TIME_STAMP, timeStamp.toString()
                    )
                }
            } else {
                Log.d(TAG, "deviceSettingApiData.data is null")
            }
        }
    }

    fun fetchAndSaveSiteJobList() {
        scope.launch(Dispatchers.IO) {
            val siteJobData = async { repository.postSiteJobList() }.await()
            Log.d(SplashViewModel.TAG, "siteJobData : $siteJobData")
            if (siteJobData.data != null) {
                val edHelper = com.allocate.ontime.encryption.EDHelper
                if (siteJobData.data?.isSuccessful == true) {
                    val data = siteJobData.data!!.body()?.data
                    Log.d(TAG, "data : $data")
                    val decryptedData = edHelper.decrypt(data.toString())
                    Log.d(TAG, "decryptedData : $decryptedData")
                    val response = Gson().fromJson(decryptedData, SiteJobResponse::class.java)
                    Log.d(TAG, "response : $response")
                    response.ResponsePacket.forEach { site->
                        addSiteList(site = Site(
                            Id = site.Id.toLong(),
                            Name = site.Name,
                            LocationLatitude = site.LocationLatitude,
                            Radius = site.Radius,
                            LocationLongitude = site.LocationLongitude,
                            Major = site.Major,
                            IsBeaconRequired = site.IsBeaconRequired,
                            timestamp = site.timestamp
                        ))
                        site.JobList.forEach {job->
                            addJobList(job = Job(
                                JobId = job.JobId.toLong(),
                                JobName = job.JobName,
                                SiteId = site.Id.toLong()
                            ))
                        }
                    }
                }
            } else {
                Log.d(TAG, "siteJobData.data is null")
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

    private suspend fun addSiteList(site: Site) =
        scope.launch(Dispatchers.IO) {
           async { daoRepository.addSiteList(site) }.await()  }

    private suspend fun addJobList(job: Job) =
        scope.launch(Dispatchers.IO) {
           async { daoRepository.addJobList(job) }.await()  }

    private suspend fun updateSiteList(site: Site) =
        scope.launch(Dispatchers.IO) { daoRepository.updateSiteList(site) }

    private suspend fun updateJobList(job: Job) =
        scope.launch(Dispatchers.IO) { daoRepository.updateJobList(job) }
}