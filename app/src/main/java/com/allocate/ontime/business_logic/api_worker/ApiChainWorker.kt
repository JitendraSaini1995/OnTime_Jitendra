package com.allocate.ontime.business_logic.api_worker

import android.annotation.SuppressLint
import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.Constraints
import androidx.work.CoroutineWorker
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import com.allocate.ontime.business_logic.api_manager.SyncManagerService
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit

@HiltWorker
class ApiChainWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val syncManagerService: SyncManagerService
) : CoroutineWorker(context, params) {
    override suspend fun doWork(): Result {
        return withContext(Dispatchers.IO) {
            try {
                syncManagerService.sync()
                Result.success()
            } catch (e: Exception) {
                Result.failure()
            }
        }
    }

    companion object {
        @SuppressLint("EnqueueWork")
        fun startPeriodicWork(context: Context) {
            val constraints =
                Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
            val periodicApiChain = PeriodicWorkRequestBuilder<ApiChainWorker>(
                repeatInterval = 15, repeatIntervalTimeUnit = TimeUnit.MINUTES
            ).setConstraints(constraints)
                .setInitialDelay(0, TimeUnit.MINUTES)
                .build()

            WorkManager.getInstance(context).enqueueUniquePeriodicWork(
                "ApiChainWorker", ExistingPeriodicWorkPolicy.KEEP, periodicApiChain
            )
        }
    }
}
