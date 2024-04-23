package com.allocate.ontime.business_logic.data.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "EmployeeBiometric")
data class EmployeeBiometric(

    @PrimaryKey
    @field:SerializedName("ID")
    var iD: String = "",

    @field:SerializedName("FaceData")
    var faceData: String? = null,

    @field:SerializedName("FaceImage")
    var faceImage: String? = null,

    @field:SerializedName("Fingerprint")
    var fingerprint: String? = null,

    @field:SerializedName("Thumbprint")
    var thumbprint: String? = null
)