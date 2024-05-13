package com.allocate.ontime.business_logic.network

import com.allocate.ontime.business_logic.utils.Constants
import com.allocate.ontime.encryption.EDModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface DeviceSettingApi {
    @POST(value = Constants.DEVICE_SETTING_URL_ENDPOINT)
    suspend fun getCIDeviceSettingDetails(
        @Body encryptedDeviceSettingInfo: EDModel
    ): Response<EDModel>
}