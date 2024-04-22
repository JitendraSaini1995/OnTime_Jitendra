package com.allocate.ontime.business_logic.api_worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.allocate.ontime.business_logic.api_manager.ApiManager
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@HiltWorker
class SiteJobListWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val apiManager: ApiManager
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        return withContext(Dispatchers.IO){
            try {
                apiManager.fetchAndSaveSiteJobList()
                Result.success()
            } catch (e: Exception) {
                Result.failure()
            }
        }
    }
}