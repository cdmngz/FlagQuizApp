package com.example.flagquizapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.flagquizapp.model.Continents
import androidx.compose.ui.Alignment

@Composable
fun ContinentSelectionScreen(
    onSelectContinent: (Continents) -> Unit,
    onGoBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("🌍 Pick a Continent", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(24.dp))

        Continents.entries.forEach { continent ->
            Button(
                onClick = { onSelectContinent(continent) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(continent.displayName)
            }
            Spacer(Modifier.height(16.dp))
        }

        Spacer(Modifier.weight(1f))
        TextButton(onClick = onGoBack) {
            Text("← Back")
        }
    }
}
