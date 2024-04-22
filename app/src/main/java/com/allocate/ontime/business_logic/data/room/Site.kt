package com.allocate.ontime.business_logic.data.room

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "site_list")
data class Site(
    @PrimaryKey
    var Id: Long = 0L,
    @ColumnInfo(name = "Name")
    var Name: String = "",
    @ColumnInfo(name = "Location_Latitude")
    var LocationLatitude: Double? = null,
    @ColumnInfo(name = "Location_Longitude")
    var LocationLongitude: Double? = null,
    @ColumnInfo(name = "Radius")
    var Radius: Int? = 0,
    @ColumnInfo(name = "Major")
    var Major: Int? = 0,
    @ColumnInfo(name = "IsBeaconRequired")
    var IsBeaconRequired: Boolean? = false,
    @ColumnInfo(name = "timestamp")
    var timestamp: Long? = 0L
)