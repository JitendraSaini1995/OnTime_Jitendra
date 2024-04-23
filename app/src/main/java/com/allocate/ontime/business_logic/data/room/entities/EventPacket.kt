package com.allocate.ontime.business_logic.data.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity
data class EventPacket(

    @PrimaryKey(autoGenerate = true)
    var LocalId: Int? = null,
    var ID: String? = null,
    var isEmployee: Boolean = true,
    var TimeStamp: String? = null,
    var DeviceId: String? = null,
    var EventDateTime: String? = null,
    var LoginType: String? = null,
    var AttendanceEventType: String? = null,
    var SourceDeviceType: String? = null,
    var UserId: String? = null,
    var VisitorAppId: String? = null,
    var FirstAttemptTime: String? = null,
    var RetryCount: Int = 0,
    var BlockedSince: String? = null,
    var needSync: Boolean = false,
    var MinTemperature: String? = null,
    var MaxTemperature: String? = null,
    var eventStatus: Int = 1,
    var error: String? = null,

    @SerializedName("SiteId")
    var siteId: String? = null,

    @SerializedName("JobId")
    var jobId: String? = null,

    @SerializedName("EventMadeBy")
    var EventMadeBy: String? = null,

    @SerializedName("Temperature")
    var temp: String = "0"
)