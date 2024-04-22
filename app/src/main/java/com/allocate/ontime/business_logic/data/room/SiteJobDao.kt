package com.allocate.ontime.business_logic.data.room

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface SiteJobDao {
    @Query(value = "SELECT * from site_list")
    fun getSiteJobList(): Flow<List<Site>>
    @Upsert
    suspend fun insertSite(site: Site)

    @Upsert
    suspend fun insertJob(job: Job)

    @Query("Select timestamp from site_list Order by timestamp desc limit 1")
    suspend fun getSiteTimestamp(): Long?

    @Update
    suspend fun updateSite(site: Site)

    @Update
    suspend fun updateJob(job: Job)
}