package com.allocate.ontime.presentation_logic.model

import com.google.gson.annotations.SerializedName

data class EmployeeResponse(
    @SerializedName("ResponseCode")
    val responseCode: String,
    @SerializedName("ResponsePacket")
    val employeeResponsePacket: EmployeeResponsePacket,
    @SerializedName("ResponseMessage")
    val responseMessage: String,
    @SerializedName("IsSuccess")
    val isSuccess: Boolean
)

data class EmployeeResponsePacket(
    @SerializedName("lstEmployee")
    val lstEmployee: List<Employee>,
    @SerializedName("DeletedIds")
    val deletedIds: List<Int>,
    @SerializedName("TotalRecordInPage")
    val totalRecordInPage: Int,
    @SerializedName("TotalPagesSize")
    val totalPagesSize: Int,
)

data class Employee(
    @SerializedName("ID")
    val id: Int,
    @SerializedName("TimeClockPin")
    val timeClockPin: String,
    @SerializedName("PINNumber")
    val pinNumber: String?,
    @SerializedName("EmployeeNumber")
    val employeeNumber: String,
    @SerializedName("FirstName")
    val firstName: String,
    @SerializedName("LastName")
    val lastName: String,
    @SerializedName("UserName")
    val userName: String,
    @SerializedName("ClintID")
    val clintID: String,
    @SerializedName("Fingerprint")
    val fingerprint: String,
    @SerializedName("Thumbprint")
    val thumbprint: String,
    @SerializedName("FaceData")
    val faceData: String,
    @SerializedName("FaceImage")
    val faceImage: String?,
    @SerializedName("IsFob")
    val isFob: Boolean,
    @SerializedName("RoleId")
    val roleId: Int,
    @SerializedName("UserRole")
    val userRole: Int,
    @SerializedName("TrustOrganization")
    val trustOrganization: String,
    @SerializedName("IsAllOffice")
    val isAllOffice: Boolean,
    @SerializedName("Redius")
    val redius: Int,
    @SerializedName("TimeClockIdentifier")
    val timeClockIdentifier: String,
    @SerializedName("Updated")
    val updated: Long,
    @SerializedName("TotaleEmployee")
    val totalEmployee: Int,
    @SerializedName("IsRegistered")
    val isRegistered: Boolean,
    @SerializedName("IsFacialDevice")
    val isFacialDevice: Boolean,
    @SerializedName("DeviceID")
    val deviceID: Int,
    @SerializedName("FobVersion")
    val fobVersion: Int,
    @SerializedName("IsDeleted")
    val isDeleted: Boolean,
    @SerializedName("RegistrationDate")
    val registrationDate: String?,
    @SerializedName("SwipeAndGo")
    val swipeAndGo: Boolean,
    @SerializedName("needSync")
    val needSync: Boolean,
    @SerializedName("isLastPage")
    val isLastPage: Boolean
)
