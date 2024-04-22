package com.allocate.ontime.business_logic.data.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "job_list")
data class Job(
    @PrimaryKey
    @ColumnInfo(name = "Job_Id")
    var JobId: Long = 0,
    @ColumnInfo(name = "Job_Name")
    var JobName: String? = "",
    @ColumnInfo(name = "Site_Id")
    var SiteId: Long = 0
)
