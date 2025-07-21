package com.example.flagquizapp.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.flagquizapp.model.Continent
import com.example.flagquizapp.model.Subregion

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubRegionScreen(
    continent: Continent,
    onGoBack: () -> Unit,
    onSelectSubregion: (Subregion) -> Unit
) {
    val subregions = remember(continent) {
        Subregion.entries.filter { it.continent == continent }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("📍Subregions of ${continent.displayName}") },
                navigationIcon = {
                    IconButton(onClick = onGoBack) {
                        Icon(Icons.AutoMirrored.Filled.KeyboardArrowLeft, contentDescription = "Go back")
                    }
                }
            )
        }
    ) { contentPadding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = contentPadding,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            items(
                items = subregions,
                key = { it.name }
            ) { subregion ->
                ListItem(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onSelectSubregion(subregion) },
                    headlineContent = { Text(subregion.displayName) },
                    trailingContent = {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                            contentDescription = "Select ${subregion.displayName}"
                        )
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SubRegionScreenPreview() {
    MaterialTheme {
        SubRegionScreen(
            continent = Continent.ASIA,
            onGoBack = {},
            onSelectSubregion = {}
        )
    }
}
