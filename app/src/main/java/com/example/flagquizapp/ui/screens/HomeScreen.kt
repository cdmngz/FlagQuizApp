package com.example.flagquizapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onPlayDailyGame: () -> Unit,
    onPlayWorldQuiz: () -> Unit,
    onPlayCapitalQuiz: () -> Unit,
    onPlayFootballQuiz: () -> Unit,
    onOpenSettings: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("👋🏻 Welcome", style = MaterialTheme.typography.headlineMedium) },
                actions = {
                    IconButton(onClick = onOpenSettings) {
                        Icon(Icons.Filled.Settings, contentDescription = "Settings")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(32.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(onClick = onPlayDailyGame) { Text("🦜️ Daily Game") }
            Spacer(Modifier.height(16.dp))

            Button(onClick = onPlayWorldQuiz) { Text("🌎 World Flags") }
            Spacer(Modifier.height(16.dp))

            Button(onClick = onPlayCapitalQuiz) { Text("🏰 Capital Flags (Soon...)") }
            Spacer(Modifier.height(16.dp))

            Button(onClick = onPlayFootballQuiz) { Text("⚽️ Football Flags (Soon...)") }
            Spacer(Modifier.weight(1f))
        }
    }
}
