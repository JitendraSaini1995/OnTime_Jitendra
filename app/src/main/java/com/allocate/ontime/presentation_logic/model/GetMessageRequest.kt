package com.allocate.ontime.presentation_logic.model

import com.allocate.ontime.business_logic.data.room.entities.ListUserRead
import com.google.gson.annotations.SerializedName

data class GetMessageRequest(
    @SerializedName("TimeStamp")
    var timeStamp: String? = null,

    @SerializedName("ListUserRead")
    var listUserRead: List<ListUserRead>? = null
)