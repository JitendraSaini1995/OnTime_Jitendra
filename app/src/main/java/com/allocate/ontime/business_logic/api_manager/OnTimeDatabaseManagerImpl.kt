package com.allocate.ontime.business_logic.api_manager

import android.content.Context
import com.allocate.ontime.business_logic.data.room.entities.EmployeePacket
import com.allocate.ontime.business_logic.data.room.entities.Job
import com.allocate.ontime.business_logic.data.room.entities.Site
import com.allocate.ontime.business_logic.data.shared_preferences.SecureSharedPrefs
import com.allocate.ontime.business_logic.repository.DaoRepository
import com.allocate.ontime.business_logic.utils.Constants
import com.allocate.ontime.presentation_logic.model.CombinedResponse
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class OnTimeDatabaseManagerImpl @Inject constructor(
    private val daoRepository: DaoRepository,
    @ApplicationContext private val context: Context,
    private val scope: CoroutineScope
) : OnTimeDatabaseManager {

    private val TAG = "OnTimeDatabaseManagerImpl"

    override fun syncDataInDb(combinedResponse: CombinedResponse) {
        combinedResponse.superAdminResponse?.ResponsePacket?.let {
            SecureSharedPrefs(context).saveData(
                Constants.USER_NAME, it.UserName
            )
        }
        combinedResponse.superAdminResponse?.ResponsePacket?.let {
            SecureSharedPrefs(context).saveData(
                Constants.PASSWORD, it.Password
            )
        }

        val deviceSettingJsonString = Gson().toJson(combinedResponse.deviceSettingResponse)
        val timeStamp = combinedResponse.deviceSettingResponse?.ResponsePacket?.TimeStamp
        SecureSharedPrefs(context).saveData(
            Constants.DEVICE_SETTING_DATA, deviceSettingJsonString
        )
        SecureSharedPrefs(context).saveData(
            Constants.TIME_STAMP, timeStamp.toString()
        )

        combinedResponse.siteJobResponse?.ResponsePacket?.forEach { site ->
            scope.launch(Dispatchers.IO) {
                addSiteList(
                    site = Site(
                        Id = site.Id.toLong(),
                        Name = site.Name,
                        LocationLatitude = site.LocationLatitude,
                        Radius = site.Radius,
                        LocationLongitude = site.LocationLongitude,
                        Major = site.Major,
                        IsBeaconRequired = site.IsBeaconRequired,
                        timestamp = site.timestamp
                    )
                )
                site.JobList.forEach { job ->
                    addJobList(
                        job = Job(
                            JobId = job.JobId.toLong(),
                            JobName = job.JobName,
                            SiteId = site.Id.toLong()
                        )
                    )
                }
            }
        }

        val totalPagesSize = combinedResponse.employeeResponse?.employeeResponsePacket?.totalPagesSize
        SecureSharedPrefs(context).saveData(
            Constants.TOTAL_PAGES_SIZE, totalPagesSize.toString()
        )
        combinedResponse.employeeResponse?.employeeResponsePacket?.lstEmployee?.forEach() { employee ->
            scope.launch(Dispatchers.IO) {
                addEmployee(
                    employeePacket = EmployeePacket(
                        iD = employee.id.toString(),
                        fingerprint = employee.fingerprint,
                        trustOrganization = employee.trustOrganization,
                        allOffice = employee.isAllOffice,
                        userName = employee.userName,
                        updated = employee.updated.toInt(),
                        firstName = employee.firstName,
                        thumbprint = employee.thumbprint,
                        employeeNumber = employee.employeeNumber,
                        clintID = employee.clintID,
                        faceData = employee.faceData,
                        faceImage = employee.faceImage,
                        timeClockPin = employee.timeClockPin,
                        roleId = employee.roleId.toString(),
                        totaleEmployee = employee.totalEmployee,
                        lastName = employee.lastName,
                        redius = employee.redius.toString(),
                        RegistrationDate = employee.registrationDate,
                        needSync = employee.needSync,
                        registered = employee.isRegistered,
                        IsSwipeAndGoEnabled = employee.swipeAndGo,
                        fobVersion = employee.fobVersion,
                        facialDevice = employee.isFacialDevice,
                        deviceID = employee.deviceID.toString(),
                        hasFob = employee.isFob,
                        isLastPage = employee.isLastPage,
                        timeClockIdentifier = employee.timeClockIdentifier
                    )
                )
            }
        }
    }

    private suspend fun addSiteList(site: Site) = daoRepository.addSiteList(site)

    private suspend fun addJobList(job: Job) = daoRepository.addJobList(job)

    private suspend fun addEmployee(employeePacket: EmployeePacket) =
        daoRepository.addEmployee(employeePacket)

    fun getEmployee() =
        daoRepository.getAllEmployee()

    fun searchEmployee(query : String) =
        daoRepository.searchEmployee(query)

//    private suspend fun updateSiteList(site: Site) =
//        scope.launch(Dispatchers.IO) { daoRepository.updateSiteList(site) }
//
//    private suspend fun updateJobList(job: Job) =
//        scope.launch(Dispatchers.IO) { daoRepository.updateJobList(job) }
//
//    private suspend fun updateSiteList(sites: Flow<Site>) {
//        sites.flowOn(Dispatchers.IO).collect { site ->
//            daoRepository.updateSiteList(site)
//        }
//    }
//
//    private suspend fun updateJobList(jobs: Flow<Job>) {
//        jobs.flowOn(Dispatchers.IO).collect { job ->
//            daoRepository.updateJobList(job)
//        }
//    }
}