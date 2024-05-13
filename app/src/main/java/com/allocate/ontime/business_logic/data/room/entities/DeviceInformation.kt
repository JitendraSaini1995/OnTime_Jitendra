package com.allocate.ontime.business_logic.data.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "device_info")
data class DeviceInformation(
    @PrimaryKey
    val id: String,
    @ColumnInfo(name = "Device_Id")
    val deviceId: Long?,
    @ColumnInfo(name = "TrustOrganization")
    val trustOrganization: String?,
    @ColumnInfo(name = "Unique_Identifier")
    val uniqueIdentifier: String?,
    @ColumnInfo(name = "Location")
    val location: String?,
    @ColumnInfo(name = "SerialNumber")
    val serialNumber: String?,
    @ColumnInfo(name = "Latitude")
    val latitude: Double?,
    @ColumnInfo(name = "Longitude")
    val longitude: Double?,
    @ColumnInfo(name = "Postcode")
    val postcode: String?,
    @ColumnInfo(name = "Status")
    val status: String?,
    @ColumnInfo(name = "ASInstance")
    val asInstance: String?,
    @ColumnInfo(name = "ASEmployeeOnlineURL")
    val asEmployeeOnlineURL: String?,
    @ColumnInfo(name = "ASApiURL")
    val asApiURL: String?,
    @ColumnInfo(name = "AppVersion")
    val appVersion: String?,
    @ColumnInfo(name = "LocationCode")
    val locationCode: String?,
    @ColumnInfo(name = "Acknowledgement_status")
    val acknowledgementStatus: Int,
    @ColumnInfo(name = "Sites")
    val sites: String?,
    @ColumnInfo(name = "SiteName")
    val siteName: String?,
    @ColumnInfo(name = "IsRLD")
    val isRLD: Boolean?,
)

