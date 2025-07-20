package com.example.flagquizapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen(
    onPlayDailyGame: () -> Unit,
    onPlayWorldQuiz: () -> Unit,
    onPlayCapitalQuiz: () -> Unit,
    onPlayFootballQuiz: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Welcome 👋🏻", style = MaterialTheme.typography.headlineMedium)


        Spacer(Modifier.height(24.dp))

        Button(onClick = onPlayDailyGame) {
            Text("🦜️ Daily Game")
        }
        Spacer(Modifier.height(16.dp))

        Button(
            onClick = onPlayWorldQuiz,
        ) {
            Text("🌎 World Flags")
        }
        Spacer(Modifier.height(16.dp))

        Button(
            onClick = onPlayCapitalQuiz,
        ) {
            Text("🏰 Capital Flags (Soon...)")
        }
        Spacer(Modifier.height(16.dp))

        Button(
            onClick = onPlayFootballQuiz,
        ) {
            Text("⚽️ Football Flags (Soon...)")
        }
        Spacer(Modifier.height(16.dp))
    }
}
