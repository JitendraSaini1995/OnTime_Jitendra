package com.allocate.ontime.business_logic.data.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [DeviceInformation::class,Site::class,Job::class], version = 1, exportSchema = false)
abstract class OnTimeDatabase: RoomDatabase() {
    abstract fun deviceInfoDao(): DeviceInfoDao
    abstract fun siteJobDao(): SiteJobDao
}