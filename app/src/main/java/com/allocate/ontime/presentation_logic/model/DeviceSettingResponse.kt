package com.allocate.ontime.presentation_logic.model

data class DeviceSettingResponse(
    val ResponseCode: String,
    val ResponsePacket: DeviceSettingResponsePacket,
    val ResponseMessage: String,
    val IsSuccess: Boolean
)

data class DeviceSettingResponsePacket(
    val TemperatureSetting: TemperatureSetting,
    val DeviceSetting: DeviceSetting,
    val DeviceInfoSetting: DeviceInfoSetting,
    val SystemSetting: SystemSetting,
    val DeviceStatus: Int,
    val TimeStamp: Double
)

data class TemperatureSetting(
    val ID: Int,
    val MinTemperature: Double,
    val MaxTemperature: Double,
    val NormalEventGap: Int,
    val RestrictUser: Boolean,
    val RetryCount: Int,
    val RetryInterval: String,
    val BlockTime: String
)

data class DeviceSetting(
    val Id: Int,
    val TwoStepVerificationAllow: Boolean,
    val DeleteTimeOfSentEventOnHr: Any?, // Handle null value
    val InstallationDoc: String,
    val TimeStamp: Double,
    val IsFPEnable: Boolean,
    val PinNumberLength: Int,
    val IsFob: Boolean,
    val EmployeeOnline: Boolean,
    val DeviceStatus: Int,
    val DynamicExceptionTime: String,
    val IsBiometric: Boolean,
    val IsOTM: String,
    val MissedClockedInTime: String
)

data class DeviceInfoSetting(
    val ID: Int,
    val MinBattery: Int,
    val MaxStorage: Int,
    val WifiDisCount: Int,
    val PowerDisCount: Int,
    val DeviceSendInfoTime: Int,
    val EventLastHours: Int,
    val EmailTime: Any?, // Handle null value
    val UpdatedOn: String,
    val SendInfoToServer: Boolean
)

data class SystemSetting(
    val Id: Int,
    val IsHealthQuestion: Boolean,
    val TimeStamp: Double,
    val IsAutoRegisterFromHR: Boolean,
    val AllowGPS: Boolean,
    val AllowBeacon: Boolean,
    val MobileExceptionReport: Boolean,
    val AllowOTM: Boolean,
    val AllowFacial: Boolean,
    val UpdateRadius: Boolean,
    val EmployeeSync: Int,
    val Site: String,
    val Job: String,
    val EnableJobs: Boolean
)


