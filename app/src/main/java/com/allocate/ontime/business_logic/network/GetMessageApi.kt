package com.allocate.ontime.business_logic.network

import com.allocate.ontime.business_logic.utils.Constants
import com.allocate.ontime.encryption.EDModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface GetMessageApi {
    @POST(value = Constants.GET_MESSAGE)
    suspend fun getMessage(
        @Body encryptedMessage: EDModel
    ): Response<EDModel>
}