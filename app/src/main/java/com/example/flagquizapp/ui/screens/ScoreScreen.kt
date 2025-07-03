package com.example.flagquizapp.ui.screens

import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * A reusable “final score” screen.
 *
 * @param score     the user’s earned points
 * @param total     the total possible points
 * @param onRestart callback when “Restart” is tapped
 * @param onGoBack  callback when “Go Back” is tapped
 */
@Composable
fun ScoreScreen(
    score: Int,
    total: Int,
    onRestart: () -> Unit,
    onGoBack: () -> Unit
) {
    // fallback to activity back if onGoBack doesn't finish
    val backDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "🎉 Quiz finished!",
            style = MaterialTheme.typography.headlineSmall
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Your score: $score / $total",
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { println("DEBUG BUTTON clicked") }) {
            Text("DEBUG BUTTON")
        }
        Spacer(modifier = Modifier.height(24.dp))
        Button(onClick = onRestart) {
            Text("Restart")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = {
            onGoBack()
            backDispatcher?.onBackPressed()
        }) {
            Text("Go Back")
        }
    }
}
