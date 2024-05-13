package com.allocate.ontime.business_logic.network

import com.allocate.ontime.business_logic.utils.Constants
import com.allocate.ontime.encryption.EDModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface EmployeeApi {
    @POST(value = Constants.GET_APP_EMPLOYEE)
    suspend fun getEmployee(
        @Body encryptedEmployee: EDModel
    ): Response<EDModel>
}