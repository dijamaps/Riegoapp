package com.pulso.riego.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey val username: String,
    val password: String,
    val role: String,
    val description: String
)

@Entity(tableName = "lots")
data class Lot(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val location: String? = null
)

@Entity(
    tableName = "pulses",
    foreignKeys = [ForeignKey(entity = Lot::class, parentColumns = ["id"], childColumns = ["lotId"], onDelete = ForeignKey.CASCADE)],
    indices = [Index("lotId")]
)
data class Pulse(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val lotId: Long,
    val timestamp: Long = System.currentTimeMillis(),
    val durationSeconds: Int,
    val drainedPercentage: Double
)
