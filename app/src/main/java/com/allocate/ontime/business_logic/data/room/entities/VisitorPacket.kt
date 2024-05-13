package com.allocate.ontime.business_logic.data.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


@Entity(tableName = "VisitorPacket")
data class VisitorPacket(

    @PrimaryKey
    @field:SerializedName("VisitorAppId")
    var iD: String = "",

    @field:SerializedName("Fingerprint")
    var fingerprint: String? = null,

    @field:SerializedName("Updated")
    var updated: Int? = null,

    @field:SerializedName("FirstName")
    var firstName: String? = "",

    @field:SerializedName("CompanyName")
    var companyName: String? = "",

    @field:SerializedName("VisitingPerson")
    var visitingPerson: String? = "",

    @field:SerializedName("VehicleNumber")
    var vehicleRegistration: String? = "",

    @field:SerializedName("Mobile")
    var phoneNumber: String? = "",

    @field:SerializedName("Reason")
    var reason: String? = "",

    @field:SerializedName("FaceData")
    var faceData: String? = null,

    @field:SerializedName("FaceImage")
    var faceImage: String? = null,

    @SerializedName("SiteId")
    var siteId: String? = null,

    @SerializedName("Site")
    var siteName: String? = null,

    @SerializedName("dateOfRegistration")
    var dateOfRegistration: String? = null,

    @SerializedName("dateOfVisit")
    var dateOfVisit: String? = null,

    @field:SerializedName("LastName")
    var lastName: String? = "",

    var needSync: Boolean = false,

    @field:SerializedName("IsFacialDevice")
    var facialDevice: Boolean = false,

    @field:SerializedName("DeviceID")
    var deviceID: String = "0",

    @field:SerializedName("IsDeleted")
    var isDeleted: Boolean = false
)