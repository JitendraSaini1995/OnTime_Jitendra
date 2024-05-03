package com.allocate.ontime.presentation_logic.model

data class EmployeeRequest(
    val DeviceId: String?,
    val DeviceType: String? = "Finger",
    val IsEmpity: Boolean = false,
    val PageNO:Int = 1,
    val TimeStamp: String? = "0",
    val IsFacialDevice: Boolean = false
)