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

// Pick which mode the continent ring should represent (0 = Flags)
private const val MODE_FOR_PROGRESS = 0

// Your continent icons (put svg files in res/raw)
data class ContinentOption(val continent: Continent, @RawRes val svgRes: Int)

private val continentOptions = listOf(
    ContinentOption(Continent.AFRICA, R.raw.ic_cont_africa),
    ContinentOption(Continent.AMERICA, R.raw.ic_cont_america),
    ContinentOption(Continent.ASIA, R.raw.ic_cont_asia),
    ContinentOption(Continent.EUROPE, R.raw.ic_cont_europe),
    ContinentOption(Continent.OCEANIA, R.raw.ic_cont_oceania),
    ContinentOption(Continent.ANTARCTICA, R.raw.ic_cont_antarctica)
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContinentSelectionScreen(
    onSelectContinent: (Continent) -> Unit,
    onGoBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("🌎 Pick a Continent") },
                navigationIcon = {
                    IconButton(onClick = onGoBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                            contentDescription = "Go back"
                        )
                    }
                }
            )
        }
    ) { padding ->
        ScrollColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            spacing = 24.dp
        ) {
            Text(
                "Continents",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )

            // Grid: 2 columns x 3 rows
            val options = remember { continentOptions }
            repeat(3) { row ->
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    repeat(2) { col ->
                        val idx = row * 2 + col
                        val opt = options.getOrNull(idx) ?: return@repeat
                        val progress = rememberContinentProgress(opt.continent, MODE_FOR_PROGRESS)
                        CircularImageWithLabel(
                            svgRes = opt.svgRes,
                            label = opt.continent.displayName,
                            progress = progress,
                            onClick = { onSelectContinent(opt.continent) },
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }

            Spacer(Modifier.height(8.dp))
        }
    }
}

/**
 * Average % of all subregions for a given continent & mode.
 * (Change averaging logic if you want sum / max / weighted, etc.)
 */
@Composable
private fun rememberContinentProgress(
    continent: Continent,
    modeIndex: Int
): Float {
    val context = LocalContext.current
    val subs = remember(continent) {
        Subregion.entries.filter { it.continent == continent }
    }

    // Collect each subregion % (0..100) and average
    val values = subs.map { sub ->
        val flow = remember(continent, sub, modeIndex) {
            ModeProgressStore.flow(
                context,
                ModeKey(continent.name, sub.name, modeIndex)
            )
        }
        val v by flow.collectAsState(initial = 0)
        v
    }

    return if (values.isEmpty()) 0f else values.average().toFloat() / 100f
}
