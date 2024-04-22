package com.allocate.ontime.presentation_logic.model

//data class SiteJobResponse(
//    val isSuccess: Boolean,
//    val responseCode: String,
//    val responseMessage: String,
//    val responsePacket: List<Site>
//)
//
//data class Site(
//    val id: Int,
//    val isBeaconRequired: Boolean,
//    val locationLatitude: Double,
//    val locationLongitude: Double,
//    val major: Int,
//    val name: String,
//    val radius: Int,
//    val jobList: List<Job>,
//    val timestamp: Int
//)
//
//data class Job(
//    val jobId: Int,
//    val jobName: String,
//    val siteId: Int
//)


data class SiteJobResponse(
    val IsSuccess: Boolean,
    val ResponseCode: String,
    val ResponseMessage: String,
    val ResponsePacket: List<Site>
)

data class Site(
    val Id: Int,
    val IsBeaconRequired: Boolean,
    val LocationLatitude: Double,
    val LocationLongitude: Double,
    val Major: Int,
    val Name: String,
    val Radius: Int,
    val JobList: List<Job>,
    val timestamp: Long
)

data class Job(
    val JobId: Int,
    val JobName: String,
    val SiteId: Int
)



