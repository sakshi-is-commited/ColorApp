package com.example.colorapp.repository

import com.example.colorapp.data.ColorDao
import com.example.colorapp.data.ColorItem
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class ColorRepository(private val colorDao: ColorDao) {

    private val firebaseDatabase = FirebaseDatabase.getInstance().getReference("colors")

    fun getColors(): Flow<List<ColorItem>> = colorDao.getColors()

    fun getPendingColorsCount(): Flow<Int> = colorDao.getPendingCount()

    suspend fun addColor(colorItem: ColorItem) {
        colorDao.addColor(colorItem)
    }

    suspend fun syncPendingColors() {
        val unsyncedColors = colorDao.getColors().first().filter { !it.isSynced }
        unsyncedColors.forEach { color ->
            firebaseDatabase.push().setValue(color)
        }
        colorDao.markColorsAsSynced()
    }
}
