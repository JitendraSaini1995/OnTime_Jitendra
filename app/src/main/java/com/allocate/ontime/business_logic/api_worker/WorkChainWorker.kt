package com.allocate.ontime.business_logic.api_worker

import android.annotation.SuppressLint
import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters

class WorkChainWorker(
    context: Context,
    params: WorkerParameters,
) : CoroutineWorker(context, params) {

    @SuppressLint("EnqueueWork")
    override suspend fun doWork(): Result {
        return try {
            val superAdminWorkerRequest = OneTimeWorkRequestBuilder<SuperAdminWorker>().build()
            val deviceSettingWorkerRequest =
                OneTimeWorkRequestBuilder<DeviceSettingWorker>().build()

            WorkManager.getInstance(applicationContext)
                .beginWith(superAdminWorkerRequest)
                .then(deviceSettingWorkerRequest)
                .enqueue()

            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }
}