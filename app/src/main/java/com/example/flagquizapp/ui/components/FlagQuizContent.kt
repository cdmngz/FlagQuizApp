package com.example.flagquizapp.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.flagquizapp.model.Country

@Composable
fun FlagQuizContent(
    correct: Country,
    options: List<Country>,
    answered: Boolean,
    isCorrect: Boolean,
    clickedCountry: Country?,
    onOptionClick: (Country) -> Unit
) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(correct.name, style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(24.dp))

        options.chunked(2).forEach { rowOptions ->
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.padding(vertical = 8.dp)
            ) {
                rowOptions.forEach { country ->
                    FlagOption(
                        country = country,
                        correct = correct,
                        answered = answered,
                        clickedCountry = clickedCountry,
                        onClick = { onOptionClick(country) }
                    )
                }
            }
        }

        Spacer(Modifier.height(16.dp))

        if (answered) {
            Text(
                text = if (isCorrect) "🎉 Correct!" else "❌ Wrong",
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}
