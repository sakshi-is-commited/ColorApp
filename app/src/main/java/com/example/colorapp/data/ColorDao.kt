package com.example.colorapp.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ColorDao {
    @Query("SELECT * FROM colors")
    fun getColors(): Flow<List<ColorItem>>

    @Query("SELECT COUNT(*) FROM colors WHERE isSynced = 0")
    fun getPendingCount(): Flow<Int>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addColor(colorItem: ColorItem)

    @Query("UPDATE colors SET isSynced = 1 WHERE isSynced = 0")
    suspend fun markColorsAsSynced()
}
