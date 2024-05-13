package com.allocate.ontime.presentation_logic.model

data class CombinedResponse(
    var superAdminResponse: SuperAdminResponse? = null,
    var deviceSettingResponse: DeviceSettingResponse? = null,
    var siteJobResponse: SiteJobResponse? = null,
    var employeeResponse: EmployeeResponse? = null
)