package com.example.flagquizapp.ui.screens

import androidx.annotation.RawRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.flagquizapp.R
import com.example.flagquizapp.data.ModeKey
import com.example.flagquizapp.data.ModeProgressStore
import com.example.flagquizapp.model.Continent
import com.example.flagquizapp.model.Subregion
import com.example.flagquizapp.ui.components.common.CircularImageWithLabel
import com.example.flagquizapp.ui.components.common.ScrollColumn

// -----------------------------------------------------------------------------
// Unified model for each mode option
data class ModeOption(val label: String, @RawRes val svgRes: Int)

private val modeOptions = listOf(
    ModeOption("Flags", R.raw.ic_mode_flags),
    ModeOption("Maps", R.raw.ic_mode_maps),
    ModeOption("Flags / Maps", R.raw.ic_mode_both),
    ModeOption("Timed", R.raw.ic_mode_timer)
)
// -----------------------------------------------------------------------------

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
                        Icon(
                            Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                            contentDescription = "Go back"
                        )
                    }
                }
            )
        }
    ) { contentPadding ->
        ScrollColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
                .padding(8.dp),
            spacing = 12.dp
        ) {
            // ----- "All" block -----
            Text(
                text = "All",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )

            val allProgresses = rememberModeProgresses(continent, null)
            QuizOptionGrid(
                onSelect = onSelectAll,
                progresses = allProgresses
            )

            Spacer(Modifier.height(8.dp))

            // ----- Each subregion -----
            subregions.forEach { subregion ->
                Text(
                    text = subregion.displayName,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )

                val subProgresses = rememberModeProgresses(continent, subregion)
                SubregionButtonGrid(
                    subregion = subregion,
                    onSelect = onSelectSubregion,
                    progresses = subProgresses
                )
            }
        }
    }
}

/** Grid for the global "All" options */
@Composable
private fun QuizOptionGrid(
    onSelect: (Int) -> Unit,
    progresses: List<Float>
) {
    repeat(2) { rowIndex ->
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            repeat(2) { colIndex ->
                val idx = rowIndex * 2 + colIndex
                val mode = modeOptions[idx]
                CircularImageWithLabel(
                    svgRes = mode.svgRes,
                    label = mode.label,
                    progress = progresses[idx],
                    onClick = { onSelect(idx) },
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
    Spacer(Modifier.height(16.dp))
}

/** Grid for each subregion */
@Composable
fun SubregionButtonGrid(
    subregion: Subregion,
    onSelect: (Subregion, Int) -> Unit,
    progresses: List<Float>
) {
    repeat(2) { rowIndex ->
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            repeat(2) { colIndex ->
                val idx = rowIndex * 2 + colIndex
                val mode = modeOptions[idx]
                CircularImageWithLabel(
                    svgRes = mode.svgRes,
                    label = mode.label,
                    progress = progresses[idx],
                    onClick = { onSelect(subregion, idx) },
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
    Spacer(Modifier.height(16.dp))
}

// Collect saved % values (0f..1f) for each mode
@Composable
private fun rememberModeProgresses(
    continent: Continent,
    subregion: Subregion?
): List<Float> {
    val context = LocalContext.current
    return modeOptions.indices.map { idx ->
        val flow = remember(continent, subregion, idx) {
            ModeProgressStore.flow(
                context,
                ModeKey(continent.name, subregion?.name, idx)
            )
        }
        val value by flow.collectAsState(initial = 0)
        value / 100f
    }
}
