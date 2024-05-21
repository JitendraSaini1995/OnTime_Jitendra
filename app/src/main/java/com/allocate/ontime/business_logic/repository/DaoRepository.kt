package com.allocate.ontime.business_logic.repository


import com.allocate.ontime.business_logic.data.room.dao.DeviceInfoDao
import com.allocate.ontime.business_logic.data.room.dao.EmployeeDao
import com.allocate.ontime.business_logic.data.room.dao.MessageDao
import com.allocate.ontime.business_logic.data.room.entities.DeviceInformation
import com.allocate.ontime.business_logic.data.room.entities.Job
import com.allocate.ontime.business_logic.data.room.entities.Site
import com.allocate.ontime.business_logic.data.room.dao.SiteJobDao
import com.allocate.ontime.business_logic.data.room.entities.EmployeePacket
import com.allocate.ontime.business_logic.data.room.entities.ListUserRead
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class DaoRepository @Inject constructor(
    private val deviceInfoDao: DeviceInfoDao,
    private val siteJobDao: SiteJobDao,
    private val employeeDao: EmployeeDao,
    private val messageDao: MessageDao
) {
    suspend fun addDeviceInfo(deviceInformation: DeviceInformation) =
        deviceInfoDao.insert(deviceInformation)

    suspend fun addSiteList(site: Site) =
        siteJobDao.insertSite(site)

    suspend fun addJobList(job: Job) =
        siteJobDao.insertJob(job)

    suspend fun addEmployee(employeePacket: EmployeePacket) =
        employeeDao.insertEmployee(employeePacket)

    suspend fun getSiteTimestamp() =
        siteJobDao.getSiteTimestamp()

    fun getAllDeviceInfo(): kotlinx.coroutines.flow.Flow<List<DeviceInformation>> =
        deviceInfoDao.getDeviceInfo().flowOn(Dispatchers.IO).conflate()

    fun getAllSiteJobList(): kotlinx.coroutines.flow.Flow<List<Site>> =
        siteJobDao.getSiteJobList().flowOn(Dispatchers.IO).conflate()

    fun getAllEmployee(): kotlinx.coroutines.flow.Flow<List<EmployeePacket>> =
        employeeDao.getEmployee().flowOn(Dispatchers.IO).conflate()

    fun searchEmployee(query : String): kotlinx.coroutines.flow.Flow<List<EmployeePacket>> =
        employeeDao.searchEmployees(query).flowOn(Dispatchers.IO).conflate()
    suspend fun getMessageTimeStamp(): String? =
        messageDao.getMessageTimeStamp()

    suspend fun getReadedMesseges(needSync: Boolean): List<ListUserRead> =
        messageDao.getReadedMesseges(needSync)

    suspend fun updateDeviceInfo(deviceInformation: DeviceInformation) =
        deviceInfoDao.update(deviceInformation)

    suspend fun updateSiteList(site: Site) =
        siteJobDao.updateSite(site)

    suspend fun updateJobList(job: Job) =
        siteJobDao.updateJob(job)
}