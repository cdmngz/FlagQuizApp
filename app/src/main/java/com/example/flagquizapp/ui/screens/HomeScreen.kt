package com.example.flagquizapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen(
    onPlayWorldQuiz: () -> Unit,
    onPlayCapitalQuiz: () -> Unit,
    onPlayFootballQuiz: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("🌍 Flag Quiz App", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(24.dp))

        Button(onClick = onPlayWorldQuiz) {
            Text("🌎 World Flags")
        }
        Spacer(Modifier.height(16.dp))

        Button(onClick = onPlayCapitalQuiz) {
            Text("🏰 Capital Flags")
        }
        Spacer(Modifier.height(16.dp))

        Button(onClick = onPlayFootballQuiz) {
            Text("⚽️ Football Flags")
        }
        Spacer(Modifier.height(16.dp))
    }
}
