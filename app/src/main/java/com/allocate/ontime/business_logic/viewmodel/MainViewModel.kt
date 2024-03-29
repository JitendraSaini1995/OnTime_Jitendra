package com.allocate.ontime.business_logic.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.allocate.ontime.business_logic.data.shared_preferences.SecureSharedPrefs
import com.allocate.ontime.business_logic.repository.DeviceInfoRepository
import com.allocate.ontime.business_logic.utils.Constants
import com.allocate.ontime.encryption.EDModel
import com.allocate.ontime.presentation_logic.model.SuperAdminResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@HiltViewModel
@SuppressLint("StaticFieldLeak")
class MainViewModel @Inject constructor(
    private val repository: DeviceInfoRepository,
    private val context: Context
) : ViewModel() {

    companion object{
        const val TAG = "MainViewModel"
    }

    init {
        fetchSuperAdminDetails()
    }

    private fun fetchSuperAdminDetails() {
        viewModelScope.launch {
            val result = repository.postSuperAdminDetails()
            Log.d(TAG,"result : $result")
            if (result.data != null) {
                if (result.data?.isSuccessful == true) {
                    val data = result.data?.body()?.data
                    Log.d(TAG,"postSuperAdminDetails data : $data")
                    val decryptedData =
                        EDModel(data.toString()).getResponseModel(SuperAdminResponse::class.java)
                    Log.d(TAG,"decryptedData : $decryptedData")
                    SecureSharedPrefs(context).saveData(
                        Constants.USER_NAME,
                        decryptedData.ResponsePacket.UserName
                    )
                    SecureSharedPrefs(context).saveData(
                        Constants.PASSWORD,
                        decryptedData.ResponsePacket.Password
                    )
                }
            } else {
                Log.d(TAG, "result.data is null")
            }
        }
    }
}