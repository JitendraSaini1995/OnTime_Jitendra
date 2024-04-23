package com.allocate.ontime.business_logic.data.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class DailyAnswer (

    @SerializedName("ID")
    @PrimaryKey(autoGenerate = true)
    var iD: Int? = null,

    @SerializedName("Question_Id")
    var questionId: String = "0",

    @SerializedName("DeviceId")
    var deviceId: String? = null,

    @SerializedName("EmployeeId")
    var employeeId: String? = "0",

    @SerializedName("TimeStamp")
    var timeStamp: String = "0",

    @SerializedName("IsPositive")
    var isIsPositive: Boolean = false,

    @SerializedName("Answer")
    var answer: String? = null,

    var needSync: Boolean = false,

    @SerializedName("GroupDateId")
    var groupId: String = "0"

)