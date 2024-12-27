package com.example.colorapp.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.colorapp.data.ColorItem

@Composable
fun ColorListScreen(
    colors: List<ColorItem>,
    pendingSyncCount: Int,
    onAddColor: () -> Unit,
    onSync: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Header Row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Color App",
                style = MaterialTheme.typography.titleLarge,
                color = Color(0xFF6200EE)
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Sync,
                    contentDescription = "Sync",
                    tint = Color.Gray
                )
                Text(
                    text = pendingSyncCount.toString(),
                    modifier = Modifier.padding(start = 4.dp),
                    color = Color.Gray
                )
            }
        }

        // Color List
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            items(colors) { color ->
                ColorCard(color = color)
            }
        }

        // Footer Buttons
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = onAddColor,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF7E6ACF))
            ) {
                Text("Add Color")
            }
            Button(
                onClick = onSync,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF7E6ACF))
            ) {
                Text("Sync")
            }
        }
    }
}

@Composable
fun ColorCard(color: ColorItem) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .background(Color(android.graphics.Color.parseColor(color.color)))
            .padding(16.dp)
    ) {
        Text(
            text = color.color,
            style = MaterialTheme.typography.bodyLarge,
            color = Color.White
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Created at ${color.time}",
            style = MaterialTheme.typography.bodySmall,
            color = Color.White
        )
    }
}
