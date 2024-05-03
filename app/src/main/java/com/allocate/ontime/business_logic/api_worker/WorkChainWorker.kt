package com.allocate.ontime.business_logic.api_worker

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.work.Constraints
import androidx.work.CoroutineWorker
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import java.util.concurrent.TimeUnit

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
            val siteJobListWorkerRequest =
                OneTimeWorkRequestBuilder<SiteJobListWorker>().build()
            val employeeWorkerRequest =
                OneTimeWorkRequestBuilder<EmployeeWorker>().build()

            WorkManager.getInstance(applicationContext)
                .beginWith(superAdminWorkerRequest)
                .then(deviceSettingWorkerRequest)
                .then(siteJobListWorkerRequest)
                .then(employeeWorkerRequest)
                .enqueue()

            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }

   companion object {
        @SuppressLint("EnqueueWork")
        fun startPeriodicWork(context: Context) {
            val constraints =
                Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
            val periodicApiChain = PeriodicWorkRequestBuilder<WorkChainWorker>(
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



//class WorkChainWorker(
//    context: Context,
//    params: WorkerParameters,
//) : CoroutineWorker(context, params) {
//
//    @SuppressLint("EnqueueWork")
//    override suspend fun doWork(): Result {
//        return try {
//            val superAdminWorkerRequest = OneTimeWorkRequestBuilder<SuperAdminWorker>().build()
//            WorkManager.getInstance(applicationContext)
//                .beginWith(superAdminWorkerRequest)
//                .enqueue()
//            Result.success()
//        } catch (e: Exception) {
//            Result.failure()
//        }
//    }
//}

//class WorkChainWorker(
//    context: Context,
//    params: WorkerParameters
//) : CoroutineWorker(context, params) {
//
//    @SuppressLint("EnqueueWork")
//    override suspend fun doWork(): Result {
//        val workerList = listOf(
//            OneTimeWorkRequestBuilder<SuperAdminWorker>().build(),
//            OneTimeWorkRequestBuilder<DeviceSettingWorker>().build(),
//            OneTimeWorkRequestBuilder<SiteJobListWorker>().build(),
//            OneTimeWorkRequestBuilder<EmployeeWorker>().build()
//        )
//
//        return try {
//            val continuation = WorkManager.getInstance(applicationContext)
//                .beginWith(workerList[0]) // Start with the first worker
//
////            for (i in 1 until workerList.size) {
////                continuation.then(workerList[i]) // Chain remaining workers
////            }
//
//            continuation.enqueue() // Enqueue the entire chained workflow
//            Result.success()
//        } catch (e: Exception) {
//            Log.e("WorkChainWorker", "Error during work chain: ${e.message}")
//            Result.failure()
//        }
//    }
//}