package com.example.flagquizapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.example.flagquizapp.data.getWorldCountries
import com.example.flagquizapp.model.Country
import com.example.flagquizapp.ui.components.daily.FlagBoxGrid
import com.example.flagquizapp.ui.components.daily.SimpleAutoComplete

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DailyGameScreen(
    country: Country,
    onGoBack: () -> Unit,
    onFinish: () -> Unit
) {
    val context = LocalContext.current
    val maxGuesses = 6

    val svgLoader = remember(context) {
        ImageLoader.Builder(context)
            .components { add(SvgDecoder.Factory()) }
            .build()
    }

    val countryNames = remember { getWorldCountries().map { it.name } }

    var textInput by rememberSaveable { mutableStateOf("") }
    var usedGuesses by rememberSaveable { mutableStateOf(listOf<String>()) }
    var revealedBoxes by rememberSaveable { mutableStateOf(listOf<Int>()) }
    var flagRevealed by rememberSaveable { mutableStateOf(false) }
    var showPopulation by rememberSaveable { mutableStateOf(false) }
    var showSummary by rememberSaveable { mutableStateOf(false) }
    var score by rememberSaveable { mutableIntStateOf(0) }
    var flagMessage by rememberSaveable { mutableStateOf<String?>(null) }
    var populationMessage by rememberSaveable { mutableStateOf<String?>(null) }

    val available by remember(countryNames, usedGuesses) {
        derivedStateOf { countryNames.filterNot { option -> option in usedGuesses } }
    }

    fun submitNameGuess(raw: String) {
        val guess = raw.trim().takeIf { it.isNotEmpty() } ?: return
        val canonicalGuess = countryNames.firstOrNull { it.equals(guess, ignoreCase = true) } ?: return
        if (canonicalGuess in usedGuesses) {
            textInput = ""
            return
        }

        usedGuesses = usedGuesses + canonicalGuess

        if (canonicalGuess.equals(country.name, ignoreCase = true)) {
            score = maxGuesses - revealedBoxes.size
            revealedBoxes = (0 until maxGuesses).toList()
            flagRevealed = true
            showPopulation = true
            flagMessage = "Correct is ${country.name}! You got +$score points 🎉"
        } else {
            val remaining = (0 until maxGuesses).filterNot(revealedBoxes::contains)
            if (remaining.isNotEmpty()) revealedBoxes = revealedBoxes + remaining.random()
            if (revealedBoxes.size >= maxGuesses) {
                flagRevealed = true
                showPopulation = true
            }
            flagMessage = null
        }
        textInput = ""
    }

    fun submitPopulationGuess(isMoreGuess: Boolean) {
        val pop = country.population
        val rawM = (pop / 1_000_000 / 10) * 10
        val threshold = (rawM.coerceAtLeast(1)) * 1_000_000
        val correct = if (isMoreGuess) pop > threshold else pop < threshold

        val approx = when {
            pop >= 1_000_000 -> "+${pop / 1_000_000}M"
            pop >= 1_000     -> "+${pop / 1_000}k"
            else             -> pop.toString()
        }
        val full = pop.formatWithDots()

        populationMessage = if (correct) {
            score += 1
            "Correct! The population is $approx ($full), +1 point"
        } else {
            "Incorrect! The population is $approx ($full)"
        }

        showPopulation = false
        showSummary = true
        textInput = ""
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("🦜 Daily Game") },
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
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {

            // Flag (hidden grid or full image)
            if (!flagRevealed) {
                FlagBoxGrid(
                    country = country,
                    revealedBoxes = revealedBoxes,
                    loader = svgLoader,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                )
            } else {
                val request = remember(country.svgUrl) {
                    ImageRequest.Builder(context).data(country.svgUrl).build()
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                ) {
                    AsyncImage(
                        model = request,
                        imageLoader = svgLoader,
                        contentDescription = country.name,
                        modifier = Modifier.matchParentSize(),
                        contentScale = ContentScale.Crop
                    )
                }
                Spacer(Modifier.height(12.dp))
                flagMessage?.let {
                    Text(text = it, style = MaterialTheme.typography.bodyLarge)
                    Spacer(Modifier.height(8.dp))
                }
            }

            // Name guess
            if (!flagRevealed) {
                SimpleAutoComplete(
                    allOptions = available,
                    text = textInput,
                    onTextChange = { textInput = it },
                    onOptionChosen = { submitNameGuess(it) },
                    modifier = Modifier.fillMaxWidth()
                )
                val trimmed = textInput.trim()
                val canSubmit = trimmed.isNotEmpty() && countryNames.any {
                    it.equals(trimmed, ignoreCase = true) && it !in usedGuesses
                }
                Button(
                    onClick = { submitNameGuess(textInput) },
                    enabled = canSubmit
                ) { Text("Submit") }

                if (usedGuesses.isNotEmpty()) {
                    Text(
                        text = "Used countries: ${usedGuesses.joinToString()}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            // Population guess
            if (showPopulation) {
                val rawM = (country.population / 1_000_000 / 10) * 10
                val thresholdM = rawM.coerceAtLeast(1)
                Text("Is the population of ${country.name} more or less than $thresholdM million?")

                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    Button(onClick = { submitPopulationGuess(true) }) { Text("More") }
                    Button(onClick = { submitPopulationGuess(false) }) { Text("Less") }
                }
            }

            // Summary
            if (showSummary) {
                Spacer(Modifier.height(16.dp))
                populationMessage?.let {
                    Text(text = it, style = MaterialTheme.typography.bodyLarge)
                    Spacer(Modifier.height(8.dp))
                }

                Text("🎉 Final score: $score", style = MaterialTheme.typography.headlineMedium)

                val encouragement = when (score) {
                    maxGuesses + 1      -> "Wow +$score pts!"
                    in 1..maxGuesses    -> "Amazing +$score pts!"
                    else                -> "Don't give up, practice is the key to learn"
                }
                Text(encouragement, style = MaterialTheme.typography.bodyLarge)

                Spacer(Modifier.height(24.dp))
                Button(
                    onClick = onFinish,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Go to homepage")
                }
            }
        }
    }
}

private fun Int.formatWithDots(): String {
    val s = toString()
    val sb = StringBuilder()
    var count = 0
    for (i in s.length - 1 downTo 0) {
        sb.append(s[i])
        count++
        if (count == 3 && i != 0) {
            sb.append('.')
            count = 0
        }
    }
    return sb.reverse().toString()
}
