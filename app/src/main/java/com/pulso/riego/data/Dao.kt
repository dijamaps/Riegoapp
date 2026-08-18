package com.pulso.riego.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface AppDao {
    // Users
    @Query("SELECT * FROM users WHERE username = :username LIMIT 1")
    suspend fun getUser(username: String): User?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User)

    @Query("SELECT * FROM users")
    fun getAllUsers(): Flow<List<User>>

    // Lots
    @Insert
    suspend fun insertLot(lot: Lot): Long

    @Query("SELECT * FROM lots")
    fun getAllLots(): Flow<List<Lot>>

    // Pulses
    @Insert
    suspend fun insertPulse(pulse: Pulse)
    
    @Query("SELECT * FROM pulses WHERE lotId = :lotId ORDER BY timestamp DESC")
    fun pulsesForLot(lotId: Long): Flow<List<Pulse>>
}
