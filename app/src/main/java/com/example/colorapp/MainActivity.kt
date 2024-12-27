package com.example.colorapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.colorapp.data.AppDatabase
import com.example.colorapp.repository.ColorRepository
import com.example.colorapp.viewmodel.ColorViewModel
import com.example.colorapp.viewmodel.ColorViewModelFactory
import com.example.colorapp.ui.ColorListScreen
import com.example.colorapp.ui.theme.ColorAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 1. Create the Room Database and get the ColorDao instance
        val colorDatabase = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "color_database"
        ).build()

        val colorDao = colorDatabase.colorDao()

        // 2. Instantiate ColorRepository with ColorDao
        val colorRepository = ColorRepository(colorDao)

        // 3. Create the ColorViewModel using a custom ViewModelFactory
        val colorViewModel: ColorViewModel = ViewModelProvider(
            this,
            ColorViewModelFactory(colorRepository)
        ).get(ColorViewModel::class.java)

        // Set up your UI using Compose
        setContent {
            // Observe the StateFlow in the ViewModel
            val colors = colorViewModel.colors.collectAsState().value
            val pendingSyncCount = colorViewModel.pendingCount.collectAsState().value

            // UI
            ColorAppTheme {
                ColorListScreen(
                    colors = colors,
                    pendingSyncCount = pendingSyncCount,
                    onAddColor = { colorViewModel.addRandomColor() },
                    onSync = { colorViewModel.syncColors() }
                )
            }
        }
    }
}
