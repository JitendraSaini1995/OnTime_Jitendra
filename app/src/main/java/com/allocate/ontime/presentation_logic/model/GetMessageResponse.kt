package com.allocate.ontime.presentation_logic.model

import com.allocate.ontime.business_logic.data.room.entities.GetMessageResponsePacket
import com.google.gson.annotations.SerializedName

data class GetMessageResponse(
    @SerializedName("ResponsePacket")
    var responsePacket: List<GetMessageResponsePacket>? = null,

    @SerializedName("ResponseCode")
    var responseCode: String? = null,

    @SerializedName("ResponseMessage")
    var responseMessage: String? = null
)