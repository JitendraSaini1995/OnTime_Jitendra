package com.allocate.ontime.business_logic.api_manager

import android.util.Log
import com.allocate.ontime.business_logic.repository.DeviceInfoRepository
import com.allocate.ontime.encryption.EDModel
import com.allocate.ontime.presentation_logic.model.CombinedResponse
import com.allocate.ontime.presentation_logic.model.DeviceSettingResponse
import com.allocate.ontime.presentation_logic.model.EmployeeResponse
import com.allocate.ontime.presentation_logic.model.SiteJobResponse
import com.allocate.ontime.presentation_logic.model.SuperAdminResponse
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch
import javax.inject.Inject

class SyncManagerServiceImpl @Inject constructor(
    private val repository: DeviceInfoRepository,
    private val scope: CoroutineScope,
    private val onTimeDatabaseManagerImpl: OnTimeDatabaseManagerImpl,
) : SyncManagerService {

    private val TAG = "SyncManagerServiceImpl"

    override fun sync() {
        scope.launch(Dispatchers.IO) {
            val combinedResponse = CombinedResponse()
            fetchSuperAdminDetails().zip(fetchDeviceSettingDetails()) { superAdminDetails, deviceSettingDetails ->
                combinedResponse.superAdminResponse = superAdminDetails
                combinedResponse.deviceSettingResponse = deviceSettingDetails
            }.zip(fetchSiteJobList()) { _, siteJobList ->
                combinedResponse.siteJobResponse = siteJobList
            }.zip(fetchEmployeeList()) { _, employeeList ->
                combinedResponse.employeeResponse = employeeList
            }.collect {
                onTimeDatabaseManagerImpl.syncDataInDb(combinedResponse)
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
        }.catch { exception ->
            emit(null)
            Log.e(TAG, "Exception during fetchSuperAdminDetails: $exception")
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
        }.catch { exception ->
            emit(null)
            Log.e(TAG, "Exception during fetchDeviceSettingDetails: $exception")
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
        }.catch { exception ->
            emit(null)
            Log.e(TAG, "Exception during fetchSiteJobList: $exception")
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
        }.catch { exception ->
            emit(null)
            Log.e(TAG, "Exception during fetchEmployeeList: $exception")
        }
    }
}