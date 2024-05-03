package com.allocate.ontime.business_logic.data.room.dao

import androidx.room.Dao
import androidx.room.Query
import com.allocate.ontime.business_logic.data.room.entities.ListUserRead

@Dao
interface MessageDao {
    @Query("SELECT MAX(timeStamp) from GetMessageResponsePacket")
    suspend fun getMessageTimeStamp(): String?
    @Query("SELECT * from ListUserRead WHERE needSync = :needSync")
    suspend fun getReadedMesseges(needSync: Boolean): List<ListUserRead>
}