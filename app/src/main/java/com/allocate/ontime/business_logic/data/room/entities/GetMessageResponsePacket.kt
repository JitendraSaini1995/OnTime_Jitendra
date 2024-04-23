package com.allocate.ontime.business_logic.data.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class GetMessageResponsePacket(

    @PrimaryKey
    @SerializedName("ID")
    var iD: String = "",

    @SerializedName("IsCancel")
    var cancel: Boolean = false,

    @SerializedName("Message")
    var message: String = "",

    @SerializedName("UserID")
    var userID: String? = "",

    @SerializedName("Title")
    var title: String = "",

    @SerializedName("IsGlobal")
    var global: Boolean = false,

    @SerializedName("TimeStamp")
    var timeStamp: String = "0"

)