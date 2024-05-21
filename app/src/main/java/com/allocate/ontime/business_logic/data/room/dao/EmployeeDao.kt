package com.allocate.ontime.business_logic.data.room.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.allocate.ontime.business_logic.data.room.entities.EmployeePacket
import kotlinx.coroutines.flow.Flow

@Dao
interface EmployeeDao {
    @Query(value = "SELECT * from EmployeePacket")
    fun getEmployee(): Flow<List<EmployeePacket>>

    @Query("SELECT * FROM EmployeePacket WHERE userName LIKE :searchQuery OR id LIKE :searchQuery")
    fun searchEmployees(searchQuery: String): Flow<List<EmployeePacket>>
    @Upsert
    suspend fun insertEmployee(employeePacket: EmployeePacket)
}