package com.allocate.ontime.business_logic.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.allocate.ontime.business_logic.data.room.dao.DeviceInfoDao
import com.allocate.ontime.business_logic.data.room.dao.SiteJobDao
import com.allocate.ontime.business_logic.data.room.entities.DailyAnswer
import com.allocate.ontime.business_logic.data.room.entities.DeviceInformation
import com.allocate.ontime.business_logic.data.room.entities.EmployeeBiometric
import com.allocate.ontime.business_logic.data.room.entities.EmployeePacket
import com.allocate.ontime.business_logic.data.room.entities.EventPacket
import com.allocate.ontime.business_logic.data.room.entities.GetMessageResponsePacket
import com.allocate.ontime.business_logic.data.room.entities.Job
import com.allocate.ontime.business_logic.data.room.entities.ListUserRead
import com.allocate.ontime.business_logic.data.room.entities.Site
import com.allocate.ontime.business_logic.data.room.entities.VisitorBiometric
import com.allocate.ontime.business_logic.data.room.entities.VisitorPacket

@Database(
    entities = [DeviceInformation::class, Site::class, Job::class, EmployeePacket::class, DailyAnswer::class, EmployeeBiometric::class, EventPacket::class, GetMessageResponsePacket::class, ListUserRead::class, VisitorBiometric::class, VisitorPacket::class],
    version = 1,
    exportSchema = false
)
abstract class OnTimeDatabase : RoomDatabase() {
    abstract fun deviceInfoDao(): DeviceInfoDao
    abstract fun siteJobDao(): SiteJobDao
}