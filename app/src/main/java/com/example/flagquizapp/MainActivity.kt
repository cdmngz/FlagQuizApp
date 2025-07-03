package com.example.flagquizapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.example.flagquizapp.ui.theme.FlagQuizAppTheme
import com.example.flagquizapp.ui.screens.ScoreScreen
import kotlinx.coroutines.delay

data class Country(val name: String, val svgUrl: String)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FlagQuizAppTheme {
                Surface {
                    FlagQuiz(
                        countries = listOf(
                            Country("Spain",   "https://upload.wikimedia.org/wikipedia/en/9/9a/Flag_of_Spain.svg"),
                            Country("France",  "https://upload.wikimedia.org/wikipedia/en/c/c3/Flag_of_France.svg"),
                            Country("Germany", "https://upload.wikimedia.org/wikipedia/en/b/ba/Flag_of_Germany.svg"),
                            Country("Brazil",  "https://upload.wikimedia.org/wikipedia/en/0/05/Flag_of_Brazil.svg"),
                            // …any others you like
                        )
                    )
                }
            }
        }
    }
}

@Composable
fun FlagQuiz(countries: List<Country>) {
    var round          by remember { mutableIntStateOf(0) }
    var answered       by remember { mutableStateOf(false) }
    var isCorrect      by remember { mutableStateOf(false) }
    var score          by remember { mutableIntStateOf(0) }
    var finished       by remember { mutableStateOf(false) }
    var clickedCountry by remember { mutableStateOf<Country?>(null) }

    val totalRounds = countries.size

    // When we're done, show the ScoreScreen
    if (finished) {
        ScoreScreen(
            score     = score,
            total     = totalRounds,
            onRestart = {
                round          = 0
                score          = 0
                finished       = false
                answered       = false
                clickedCountry = null
            },
            onGoBack  = { /* maybe finish() here */ }
        )
        return
    }

    // pick 4 + which one is correct
    val (options, correct) = remember(round) {
        val pick4 = countries.shuffled().take(4)
        pick4 to pick4.random()
    }

    // Coil SVG loader
    val loader = ImageLoader.Builder(LocalContext.current)
        .components { add(SvgDecoder.Factory()) }
        .build()

    // auto-advance to next round (or finish) 1s after answering
    LaunchedEffect(answered) {
        if (answered) {
            delay(1_000L)
            if (round < totalRounds - 1) {
                round++
                answered = false
                clickedCountry = null
            } else {
                finished = true
            }
        }
    }

    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment  = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(correct.name, style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(24.dp))

        options.chunked(2).forEach { rowOpts ->
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.padding(vertical = 8.dp)
            ) {
                rowOpts.forEach { country ->
                    // ── parent container so we can overlay the text ─────────────
                    Box(modifier = Modifier) {
                        // ── inner box: fixed size, clipped, border + click ────
                        Box(
                            modifier = Modifier
                                .size(100.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .border(
                                    BorderStroke(
                                        width = if (!answered) 2.dp else 4.dp,
                                        color = when {
                                            !answered           -> Color.Transparent
                                            country == correct  -> Color.Green
                                            else                -> Color.Red
                                        }
                                    ),
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .clickable(enabled = !answered) {
                                    clickedCountry = country
                                    isCorrect      = (country == correct)
                                    answered       = true
                                    if (isCorrect) score++
                                }
                        ) {
                            AsyncImage(
                                model       = ImageRequest.Builder(LocalContext.current)
                                    .data(country.svgUrl)
                                    .crossfade(true)
                                    .build(),
                                imageLoader = loader,
                                contentDescription = country.name,
                                modifier    = Modifier.matchParentSize()
                            )
                        }

                        // ── overlay the name on top-right when clicked ────────
                        if (answered && clickedCountry == country) {
                            Text(
                                text    = country.name,
                                style   = MaterialTheme.typography.labelSmall,
                                color   = Color.White,
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .background(
                                        Color.Black.copy(alpha = 0.6f),
                                        shape = RoundedCornerShape(bottomStart = 4.dp)
                                    )
                                    .padding(horizontal = 4.dp, vertical = 2.dp)
                            )
                        }
                    }
                }
            }
        }

        Spacer(Modifier.height(16.dp))

        if (answered) {
            Text(
                text  = if (isCorrect) "🎉 Correct!" else "❌ Wrong",
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FlagQuizPreview() {
    FlagQuizAppTheme {
        Surface {
            FlagQuiz(
                countries = listOf(
                    Country("Spain",""), Country("France",""),
                    Country("Italy",""), Country("Germany","")
                )
            )
        }
    }
}
