package com.allocate.ontime.business_logic.api_worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.allocate.ontime.business_logic.repository.DeviceInfoRepository
import com.allocate.ontime.business_logic.viewmodel.MainViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@HiltWorker
class SuperAdminWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val deviceInfoRepository: DeviceInfoRepository,
) : CoroutineWorker(context, params) {

    private val TAG = "SuperAdminWorker"

    override suspend fun doWork(): Result {
        return withContext(Dispatchers.IO){
            try {
                val viewModel = MainViewModel(deviceInfoRepository,applicationContext)
                viewModel.fetchSuperAdminDetails()
                Result.success()
            } catch (e: Exception) {
                Result.failure()
            }
        }
    }
}