package com.allocate.ontime.business_logic.api_manager

import android.annotation.SuppressLint
import android.util.Log
import com.allocate.ontime.business_logic.repository.DeviceInfoRepository
import com.allocate.ontime.encryption.EDModel
import com.allocate.ontime.presentation_logic.model.DeviceSettingResponse
import com.allocate.ontime.presentation_logic.model.EmployeeResponse
import com.allocate.ontime.presentation_logic.model.SiteJobResponse
import com.allocate.ontime.presentation_logic.model.SuperAdminResponse
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import javax.inject.Inject

class SyncManagerServiceImpl @Inject constructor(
    private val repository: DeviceInfoRepository,
    private val scope: CoroutineScope,
    private val onTimeDatabaseManagerImpl: OnTimeDatabaseManagerImpl,
) : SyncManagerService {

    private val TAG = "SyncManagerServiceImpl"

    @SuppressLint("SuspiciousIndentation")
    @OptIn(ExperimentalCoroutinesApi::class)
    override fun sync() {
        scope.launch(Dispatchers.IO) {
            val combinedFlow = listOf(
                fetchSuperAdminDetails(),
                fetchDeviceSettingDetails(),
                fetchSiteJobList(),
                fetchEmployeeList()
            ).asFlow()
                .flatMapMerge { value ->
                    value
                }.toList()
            processCombinedResponse(combinedFlow)
        }
    }

    private fun processCombinedResponse(responses: List<Any?>) {
        for (response in responses) {
            when (response) {
                is SuperAdminResponse -> onTimeDatabaseManagerImpl.syncSuperAdminDataInDb(response)
                is DeviceSettingResponse -> onTimeDatabaseManagerImpl.syncDeviceSettingDataInDb(
                    response
                )

                is SiteJobResponse -> onTimeDatabaseManagerImpl.syncSiteJobDataInDb(response)
                is EmployeeResponse -> onTimeDatabaseManagerImpl.syncEmployeeDataInDb(response)
                else -> if (response != null) {
                    throw IllegalArgumentException("Unknown response type: ${response.javaClass}")
                }
            }
        }
    }

    private suspend fun fetchSuperAdminDetails(): Flow<SuperAdminResponse?> {
        return flow {
            val result = repository.postSuperAdminDetails()
            if (result.data != null && result.data?.isSuccessful == true) {
                val data = result.data?.body()?.data
                val decryptedData =
                    EDModel(data.toString()).getResponseModel(SuperAdminResponse::class.java)
                emit(decryptedData)
            } else {
                emit(null)
                Log.d(TAG, "fetchSuperAdminDetails failed")
            }
        }
    }

    private suspend fun fetchDeviceSettingDetails(): Flow<DeviceSettingResponse?> {
        return flow {
            val deviceSettingApiData = repository.postDeviceSettingDetails()
            if (deviceSettingApiData.data != null && deviceSettingApiData.data?.isSuccessful == true) {
                val data = deviceSettingApiData.data!!.body()?.data
                val decryptedData = com.allocate.ontime.encryption.EDHelper.decrypt(data.toString())
                val response = Gson().fromJson(decryptedData, DeviceSettingResponse::class.java)
                emit(response)
            } else {
                Log.d(TAG, "fetchDeviceSettingDetails failed")
            }
        }
    }

    private suspend fun fetchSiteJobList(): Flow<SiteJobResponse> {
        return flow {
            val siteJobData = repository.postSiteJobList()
            if (siteJobData.data != null && siteJobData.data?.isSuccessful == true) {
                val data = siteJobData.data!!.body()?.data
                val decryptedData = com.allocate.ontime.encryption.EDHelper.decrypt(data.toString())
                val response = Gson().fromJson(decryptedData, SiteJobResponse::class.java)
                emit(response)
            } else {
                Log.d(TAG, "fetchSiteJobList failed")
            }
        }
    }

    private suspend fun fetchEmployeeList(): Flow<EmployeeResponse> {
        return flow {
            val employeeApiData = repository.postEmployee()
            if (employeeApiData.data != null && employeeApiData.data?.isSuccessful == true) {
                val data = employeeApiData.data!!.body()?.data
                val decryptedData = com.allocate.ontime.encryption.EDHelper.decrypt(data.toString())
                val response = Gson().fromJson(decryptedData, EmployeeResponse::class.java)
                emit(response)
            } else {
                Log.d(TAG, "fetchEmployeeList failed")
            }
        }
    }
}