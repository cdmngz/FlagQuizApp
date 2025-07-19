package com.example.flagquizapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HomeScreen(
    onPlayDailyGame: () -> Unit,
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
        Text("Welcome 👋🏻", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(24.dp))

        Button(
            onClick = onPlayDailyGame,
            shape = RoundedCornerShape(12.dp),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 10.dp)
        ) {
            Text("🗓️ Daily Game")
        }


        Spacer(Modifier.height(16.dp))

        Button(
            onClick = onPlayWorldQuiz,
            shape = RoundedCornerShape(12.dp),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 10.dp)
        ) {
            Text("🌎 World Flags")
        }
        Spacer(Modifier.height(16.dp))

        Button(
            onClick = onPlayCapitalQuiz,
            shape = RoundedCornerShape(12.dp),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 10.dp)
        ) {
            Text("🏰 Capital Flags (Soon...)")
        }
        Spacer(Modifier.height(16.dp))

        Button(
            onClick = onPlayFootballQuiz,
            shape = RoundedCornerShape(12.dp),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 10.dp)
        ) {
            Text("⚽️ Football Flags (Soon...)")
        }
        Spacer(Modifier.height(16.dp))
    }
}
