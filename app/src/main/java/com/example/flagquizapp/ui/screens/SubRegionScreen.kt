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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.flagquizapp.model.Continent
import com.example.flagquizapp.model.Subregion

private val buttonLabels = listOf(
    "Flags",
    "Maps",
    "Flags / Maps",
    "Test with time"
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubRegionScreen(
    continent: Continent,
    onGoBack: () -> Unit,
    onSelectSubregion: (Subregion, Int) -> Unit,
    onSelectAll: (Int) -> Unit
) {
    val subregions = remember(continent) {
        Subregion.entries.filter { it.continent == continent }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("🗺️ Subregions of ${continent.displayName}") },
                navigationIcon = {
                    IconButton(onClick = onGoBack) {
                        Icon(Icons.AutoMirrored.Filled.KeyboardArrowLeft, contentDescription = "Go back")
                    }
                }
            )
        }
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "All",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(start = 8.dp)
            )
            QuizOptionGrid { buttonIndex -> onSelectAll(buttonIndex) }
            Spacer(Modifier.height(8.dp))

            subregions.forEach { subregion ->
                Text(
                    text = subregion.displayName,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(start = 8.dp)
                )
                SubregionButtonGrid(
                    subregion = subregion,
                    onSelect = onSelectSubregion
                )
            }
        }
    }
}

@Composable
private fun QuizOptionGrid(onSelect: (Int) -> Unit) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        for (rowIndex in 0..1) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                for (colIndex in 0..1) {
                    val buttonIndex = rowIndex * 2 + colIndex
                    Button(
                        onClick = { onSelect(buttonIndex) },
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp)
                    ) {
                        Text(buttonLabels[buttonIndex])
                    }
                }
            }
        }
        Spacer(Modifier.height(16.dp))
    }
}

// Keep this one for subregions (unchanged)
@Composable
fun SubregionButtonGrid(
    subregion: Subregion,
    onSelect: (Subregion, Int) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        for (rowIndex in 0..1) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                for (colIndex in 0..1) {
                    val buttonIndex = rowIndex * 2 + colIndex
                    Button(
                        onClick = { onSelect(subregion, buttonIndex) },
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp)
                    ) {
                        Text(buttonLabels[buttonIndex])
                    }
                }
            }
        }
        Spacer(Modifier.height(16.dp))
    }
}
