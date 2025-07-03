package com.example.flagquizapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.delay
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.example.flagquizapp.ui.theme.FlagQuizAppTheme
import com.example.flagquizapp.ui.screens.ScoreScreen

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
                            Country("Spain",    "https://upload.wikimedia.org/wikipedia/en/9/9a/Flag_of_Spain.svg"),
                            Country("France",   "https://upload.wikimedia.org/wikipedia/en/c/c3/Flag_of_France.svg"),
                            Country("Germany",  "https://upload.wikimedia.org/wikipedia/en/b/ba/Flag_of_Germany.svg"),
                            Country("Brazil",   "https://upload.wikimedia.org/wikipedia/en/0/05/Flag_of_Brazil.svg"),
                            // add more countries as desired
                        )
                    )
                }
            }
        }
    }
}

@Composable
fun FlagQuiz(countries: List<Country>) {
    var round    by remember { mutableIntStateOf(0) }
    var answered by remember { mutableStateOf(false) }
    var isCorrect by remember { mutableStateOf(false) }
    var score    by remember { mutableIntStateOf(0) }
    var finished by remember { mutableStateOf(false) }

    val totalRounds = countries.size

    // Show our separated ScoreScreen when done
    if (finished) {
        ScoreScreen(
            score   = score,
            total   = totalRounds,
            onRestart = {
                // reset quiz state
                round    = 0
                score    = 0
                finished = false
                answered = false
            },
            onGoBack = {
                // optionally finish the Activity:
                // (LocalContext.current as? Activity)?.finish()
            }
        )
        return
    }

    // pick options + correct
    val (options, correct) = remember(round) {
        val picks = countries.shuffled().take(4)
        picks to picks.random()
    }

    // SVG loader
    val loader = ImageLoader.Builder(LocalContext.current)
        .components { add(SvgDecoder.Factory()) }
        .build()

    // auto-advance after feedback
    LaunchedEffect(answered) {
        if (answered) {
            delay(1_000L)
            if (round < totalRounds - 1) {
                round++
                answered = false
            } else {
                finished = true
            }
        }
    }

    // Quiz UI
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment   = Alignment.CenterHorizontally,
        verticalArrangement  = Arrangement.Center
    ) {
        Text(text = correct.name, style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(24.dp))

        options.chunked(2).forEach { rowOpts ->
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.padding(vertical = 8.dp)
            ) {
                rowOpts.forEach { country ->
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(country.svgUrl)
                            .crossfade(true)
                            .build(),
                        imageLoader = loader,
                        contentDescription = country.name,
                        modifier = Modifier
                            .size(100.dp)
                            .border(
                                border = when {
                                    !answered             -> BorderStroke(2.dp, Color.Transparent)
                                    country == correct    -> BorderStroke(4.dp, Color.Green)
                                    else                  -> BorderStroke(4.dp, Color.Red)
                                },
                                shape = RoundedCornerShape(8.dp)
                            )
                            .clickable(enabled = !answered) {
                                isCorrect = (country == correct)
                                answered  = true
                                if (isCorrect) score++
                            }
                    )
                }
            }
        }

        Spacer(Modifier.height(16.dp))

        if (answered) {
            Text(
                text = if (isCorrect) "🎉 Correct!" else "❌ Sorry, that’s not it.",
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