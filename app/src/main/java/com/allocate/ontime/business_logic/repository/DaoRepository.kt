package com.allocate.ontime.business_logic.repository


import com.allocate.ontime.business_logic.data.room.DeviceInfoDao
import com.allocate.ontime.business_logic.data.room.DeviceInformation
import com.allocate.ontime.business_logic.data.room.Job
import com.allocate.ontime.business_logic.data.room.Site
import com.allocate.ontime.business_logic.data.room.SiteJobDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class DaoRepository @Inject constructor(
    private val deviceInfoDao: DeviceInfoDao,
    private val siteJobDao: SiteJobDao
) {
    suspend fun addDeviceInfo(deviceInformation: DeviceInformation) =
        deviceInfoDao.insert(deviceInformation)

    suspend fun addSiteList(site: Site) =
        siteJobDao.insertSite(site)

    suspend fun addJobList(job: Job) =
        siteJobDao.insertJob(job)

    suspend fun getSiteTimestamp() =
        siteJobDao.getSiteTimestamp()

    suspend fun updateDeviceInfo(deviceInformation: DeviceInformation) =
        deviceInfoDao.update(deviceInformation)

    suspend fun updateSiteList(site: Site) =
        siteJobDao.updateSite(site)

    suspend fun updateJobList(job: Job) =
        siteJobDao.updateJob(job)

    fun getAllDeviceInfo(): kotlinx.coroutines.flow.Flow<List<DeviceInformation>> =
        deviceInfoDao.getDeviceInfo().flowOn(Dispatchers.IO).conflate()

    fun getAllSiteJobList(): kotlinx.coroutines.flow.Flow<List<Site>> =
        siteJobDao.getSiteJobList().flowOn(Dispatchers.IO).conflate()
}