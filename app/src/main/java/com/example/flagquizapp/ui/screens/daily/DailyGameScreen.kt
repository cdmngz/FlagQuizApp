package com.example.flagquizapp.ui.screens.daily

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import com.example.flagquizapp.data.getWorldCountries
import com.example.flagquizapp.model.Country
import com.example.flagquizapp.ui.components.daily.FlagBoxGrid
import com.example.flagquizapp.ui.components.daily.SimpleAutoComplete

@Composable
fun DailyGameScreen(
    country: Country,
    onFinish: () -> Unit
) {
    val context    = LocalContext.current
    val maxGuesses = 6

    // ─── SVG‐capable Coil loader ────────────────────────────────
    val svgLoader = remember(context) {
        ImageLoader.Builder(context)
            .components { add(SvgDecoder.Factory()) }
            .build()
    }

    // ─── all country names ───────────────────────────────────────
    val countryNames = remember { getWorldCountries().map { it.name } }

    // ─── game state ─────────────────────────────────────────────
    var textInput         by remember { mutableStateOf("") }
    var usedGuesses       by remember { mutableStateOf(listOf<String>()) }
    var revealedBoxes     by remember { mutableStateOf(listOf<Int>()) }
    var flagRevealed      by remember { mutableStateOf(false) }
    var showPopulation    by remember { mutableStateOf(false) }
    var showSummary       by remember { mutableStateOf(false) }
    var score             by remember { mutableIntStateOf(0) }
    var flagMessage       by remember { mutableStateOf<String?>(null) }
    var populationMessage by remember { mutableStateOf<String?>(null) }

    // exclude tried names
    val available = remember(countryNames, usedGuesses) {
        countryNames.filterNot { it in usedGuesses }
    }

    // ─── helper to launch “report broken flag” email ────────────
    val reportIntent = Intent(Intent.ACTION_SENDTO).apply {
        data = Uri.parse(
            "mailto:support@dev.com" +
                    "?subject=" + Uri.encode("Flag issue: ${country.name}") +
                    "&body="    + Uri.encode("The flag for ${country.name} did not render properly.")
        )
    }
    fun reportBrokenFlag() {
        context.startActivity(reportIntent)
    }

    // ─── name‐guess submission ───────────────────────────────────
    fun submitNameGuess(raw: String) {
        val guess = raw.trim().takeIf { it.isNotEmpty() } ?: return
        usedGuesses = usedGuesses + guess

        if (guess.equals(country.name, ignoreCase = true)) {
            score = maxGuesses - revealedBoxes.size
            revealedBoxes   = (0 until maxGuesses).toList()
            flagRevealed    = true
            showPopulation  = true
            flagMessage     = "Correct is ${country.name}! You got +$score points 🎉"
        } else {
            val remaining = (0 until maxGuesses).filter { it !in revealedBoxes }
            if (remaining.isNotEmpty()) revealedBoxes = revealedBoxes + remaining.random()
            if (revealedBoxes.size >= maxGuesses) {
                flagRevealed   = true
                showPopulation = true
            }
            // clear any prior message
            flagMessage = null
        }

        textInput = ""
    }

    // ─── population‐guess submission ────────────────────────────
    fun submitPopulationGuess(isMoreGuess: Boolean) {
        val pop = country.population
        // round threshold (clamped ≥1M):
        val rawM      = (pop / 1_000_000 / 10) * 10
        val threshold = (rawM.coerceAtLeast(1)) * 1_000_000

        val correct = if (isMoreGuess) pop > threshold else pop < threshold

        // formatting
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
            "Incorrect! The population is $approx ($full), crazy eh"
        }

        showPopulation = false
        showSummary    = true
        textInput      = ""
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        // ─── Top row: report icon & title ─────────────────────────
        Row(
            modifier              = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment     = Alignment.CenterVertically
        ) {
            Text(
                text  = "🦜 Daily Game",
                style = MaterialTheme.typography.headlineSmall
            )
            IconButton(onClick = { reportBrokenFlag() }) {
                Icon(
                    imageVector        = Icons.Filled.Build,
                    contentDescription = "Report broken flag"
                )
            }
        }

        // ─── Flag display (partial or full) ───────────────────────
        if (!flagRevealed) {
            FlagBoxGrid(
                country       = country,
                revealedBoxes = revealedBoxes,
                loader        = svgLoader
            )
        } else {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
            ) {
                AsyncImage(
                    model              = country.svgUrl,
                    imageLoader        = svgLoader,
                    contentDescription = country.name,
                    modifier           = Modifier.matchParentSize(),
                    contentScale       = ContentScale.Crop
                )
            }
            Spacer(Modifier.height(12.dp))
            // inline flag‐guess result:
            flagMessage?.let { msg ->
                Text(
                    text  = msg,
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(Modifier.height(8.dp))
            }
        }

        // ─── Name‐guess UI ─────────────────────────────────────────
        if (!flagRevealed) {
            SimpleAutoComplete(
                allOptions     = available,
                text           = textInput,
                onTextChange   = { textInput = it },
                onOptionChosen = { submitNameGuess(it) },
                modifier       = Modifier.fillMaxWidth()
            )
            Button(
                onClick = { submitNameGuess(textInput) },
                enabled = available.any { it.equals(textInput.trim(), ignoreCase = true) }
            ) {
                Text("Submit")
            }
            if (usedGuesses.isNotEmpty()) {
                Text(
                    text  = "Used countries: ${usedGuesses.joinToString()}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

        // ─── Population‐guess UI ───────────────────────────────────
        if (showPopulation) {
            val rawM      = (country.population / 1_000_000 / 10) * 10
            val thresholdM= rawM.coerceAtLeast(1)
            Text("Is the population of ${country.name} more or less than $thresholdM million?")

            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                Button(onClick = { submitPopulationGuess(true) }) { Text("More") }
                Button(onClick = { submitPopulationGuess(false) }) { Text("Less") }
            }
        }

        // ─── Final summary & “Go to homepage” ─────────────────────
        if (showSummary) {
            Spacer(Modifier.height(16.dp))

            populationMessage?.let { msg ->
                Text(
                    text  = msg,
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(Modifier.height(8.dp))
            }

            Text(
                text  = "🎉 Final score: $score",
                style = MaterialTheme.typography.headlineMedium
            )
            val encouragement = when (score) {
                maxGuesses + 1 -> "Wow +$score pts!"
                in 1..maxGuesses -> "Amazing +$score pts!"
                else -> "Don't give up, practice is the key to learn"
            }
            Text(
                text  = encouragement,
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(Modifier.height(24.dp))

            Button(
                onClick = { onFinish() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Go to homepage")
            }
        }
    }
}

/**
 * Format an Int with dot‐separators every three digits:
 *   123      → "123"
 *   1356     → "1.356"
 *   25468    → "25.468"
 *   98534543 → "98.534.543"
 */
private fun Int.formatWithDots(): String {
    val s = this.toString()
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
