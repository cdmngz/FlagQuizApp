package com.example.flagquizapp.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SimpleAutoComplete(
    allOptions: List<String>,
    text: String,
    onTextChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    onOptionChosen: (String) -> Unit
) {
    val suggestions = remember(text) {
        if (text.isBlank()) emptyList()
        else allOptions
            .filter { it.contains(text, ignoreCase = true) }
            .take(5)
    }

    Column(modifier) {
        OutlinedTextField(
            value            = text,
            onValueChange    = { onTextChange(it) },
            singleLine       = true,
            label            = { Text("Guess the country") },
            modifier         = Modifier.fillMaxWidth()
        )

        if (suggestions.isNotEmpty()) {
            Spacer(Modifier.height(4.dp))
            Surface(
                shadowElevation = 4.dp,
                shape           = MaterialTheme.shapes.medium,
                modifier        = Modifier.fillMaxWidth()
            ) {
                LazyColumn {
                    items(suggestions) { option ->
                        Text(
                            text     = option,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    // when they tap a suggestion, notify parent _and_ clear the field
                                    onOptionChosen(option)
                                }
                                .padding(12.dp)
                        )
                    }
                }
            }
        }
    }
}
