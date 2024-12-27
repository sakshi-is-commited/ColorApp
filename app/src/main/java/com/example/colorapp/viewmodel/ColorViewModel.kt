package com.example.colorapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.colorapp.data.ColorItem
import com.example.colorapp.repository.ColorRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.random.Random

class ColorViewModel(private val repository: ColorRepository) : ViewModel() {

    private val _colors = MutableStateFlow<List<ColorItem>>(emptyList())
    val colors: StateFlow<List<ColorItem>> = _colors

    private val _pendingCount = MutableStateFlow(0)
    val pendingCount: StateFlow<Int> = _pendingCount

    init {
        fetchColors()
        updatePendingCount()
    }

    private fun fetchColors() {
        viewModelScope.launch {
            repository.getColors().collect { colorList ->
                _colors.value = colorList
            }
        }
    }

    private fun updatePendingCount() {
        viewModelScope.launch {
            repository.getPendingColorsCount().collect { count ->
                _pendingCount.value = count
            }
        }
    }

    fun addRandomColor() {
        val newColor = ColorItem(
            color = String.format("#%06X", 0xFFFFFF and Random.nextInt()),
            time = System.currentTimeMillis()
        )
        viewModelScope.launch {
            repository.addColor(newColor)
        }
    }

    fun syncColors() {
        viewModelScope.launch {
            repository.syncPendingColors()
        }
    }
}
