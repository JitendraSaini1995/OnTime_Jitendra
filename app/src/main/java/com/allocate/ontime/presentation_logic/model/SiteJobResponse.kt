package com.allocate.ontime.presentation_logic.model

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



