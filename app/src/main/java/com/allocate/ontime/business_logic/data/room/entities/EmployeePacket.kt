package com.allocate.ontime.business_logic.data.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "EmployeePacket")
data class EmployeePacket(

    @PrimaryKey
    @field:SerializedName("ID")
    var iD: String = "",

    @field:SerializedName("Fingerprint")
    var fingerprint: String? = null,

    @field:SerializedName("TrustOrganization")
    var trustOrganization: String? = null,

    @field:SerializedName("IsAllOffice")
    var allOffice: Boolean = false,

    @field:SerializedName("UserName")
    var userName: String? = null,

    @field:SerializedName("Updated")
    var updated: Int? = null,

    @field:SerializedName("FirstName")
    var firstName: String? = "",

    @field:SerializedName("Thumbprint")
    var thumbprint: String? = null,

    @field:SerializedName("EmployeeNumber")
    var employeeNumber: String? = null,

    @field:SerializedName("ClintID")
    var clintID: String? = null,

    @field:SerializedName("FaceData")
    var faceData: String? = null,

    @field:SerializedName("FaceImage")
    var faceImage: String? = null,

    @field:SerializedName("TimeClockPin")
    var timeClockPin: String? = null,

    @field:SerializedName("RoleId")
    var roleId: String? = "",

    @field:SerializedName("TotaleEmployee")
    var totaleEmployee: Int? = null,

    @field:SerializedName("LastName")
    var lastName: String? = "",

    @field:SerializedName("Redius")
    var redius: String? = "5",

    @SerializedName("RegistrationDate")
    var RegistrationDate: String? = "",

    var needSync: Boolean = false,

    @field:SerializedName("IsRegistered")
    var registered: Boolean = false,

    @field:SerializedName("SwipeAndGo")
    var IsSwipeAndGoEnabled: Boolean = false,

    @field:SerializedName("FobVersion")
    var fobVersion: Int = 0,

    @field:SerializedName("IsFacialDevice")
    var facialDevice: Boolean = false,

    @field:SerializedName("DeviceID")
    var deviceID: String = "0",

    @field:SerializedName("IsFob")
    var hasFob: Boolean = false,

    @field:SerializedName("isLastPage")
    var isLastPage: Boolean? = false,

    @field:SerializedName("TimeClockIdentifier")
    var timeClockIdentifier: String?
)