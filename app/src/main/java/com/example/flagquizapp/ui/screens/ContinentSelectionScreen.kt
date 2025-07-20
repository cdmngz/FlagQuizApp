package com.example.flagquizapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.flagquizapp.model.Continents

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
        Row(
            modifier              = Modifier.fillMaxWidth(),
            verticalAlignment     = Alignment.CenterVertically
        ) {
            IconButton(onClick = onGoBack) {
                Icon(
                    imageVector        = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                    contentDescription = "Go Back"
                )
            }
            Text(
                text  = "🌎 Pick a Continent",
                style = MaterialTheme.typography.headlineMedium
            )
        }

        Spacer(Modifier.height(32.dp))

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
    }
}
