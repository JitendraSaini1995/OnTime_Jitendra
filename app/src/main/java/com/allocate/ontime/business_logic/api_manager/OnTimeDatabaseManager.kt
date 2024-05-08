package com.allocate.ontime.business_logic.api_manager

import com.allocate.ontime.presentation_logic.model.DeviceSettingResponse
import com.allocate.ontime.presentation_logic.model.EmployeeResponse
import com.allocate.ontime.presentation_logic.model.SiteJobResponse
import com.allocate.ontime.presentation_logic.model.SuperAdminResponse

interface OnTimeDatabaseManager {
    fun syncSuperAdminDataInDb(superAdminResponse: SuperAdminResponse)
    fun syncDeviceSettingDataInDb(deviceSettingResponse: DeviceSettingResponse)
    fun syncSiteJobDataInDb(siteJobResponse: SiteJobResponse)
    fun syncEmployeeDataInDb(employeeResponse: EmployeeResponse)

}