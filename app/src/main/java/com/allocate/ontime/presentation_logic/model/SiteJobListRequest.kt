package com.allocate.ontime.presentation_logic.model

data class SiteJobListRequest(
    val employeeId: Long,
    val timestamp: Long = 0
)