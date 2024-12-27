package com.example.colorapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "colors") // This annotation is required for Room
data class ColorItem(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val color: String,
    val time: Long,
    val isSynced: Boolean = false
)
