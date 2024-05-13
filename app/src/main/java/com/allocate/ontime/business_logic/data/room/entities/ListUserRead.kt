package com.allocate.ontime.business_logic.data.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class ListUserRead(

    @PrimaryKey
    @SerializedName("MessageId")
    var messageId: String = "0",

    @SerializedName("UserId")
    var userId: String? = null,

    var needSync: Boolean = true
)