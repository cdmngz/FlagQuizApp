package com.example.flagquizapp.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.flagquizapp.ui.theme.ThemeOption

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onGoBack: () -> Unit,
    onThankYou: () -> Unit,
    currentTheme: ThemeOption,
    onThemeChange: (ThemeOption) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("✏️ Settings") },
                navigationIcon = {
                    IconButton(onClick = onGoBack) {
                        Icon(Icons.AutoMirrored.Filled.KeyboardArrowLeft, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            item { SectionHeader("APPEARANCE") }

            item {
                EnumRow(
                    icon = { Icon(Icons.Filled.Create, null) },
                    title = "Theme",
                    current = currentTheme,
                    all = ThemeOption.entries.toTypedArray(),
                    onSelect = onThemeChange
                )
            }

            item { Spacer(Modifier.height(24.dp)) }

            item {
                ClickRow(
                    icon = { Icon(Icons.Filled.Favorite, null) },
                    title = "Buy me a coffee ☕️",
                    subtitle = "Thanks for the support!",
                    onClick = onThankYou
                )
            }

            item { Spacer(Modifier.height(32.dp)) }
        }
    }
}

@Composable
private fun SectionHeader(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.labelLarge,
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
    )
}

@Composable
private fun ClickRow(
    icon: @Composable (() -> Unit)? = null,
    title: String,
    subtitle: String? = null,
    onClick: () -> Unit
) {
    ListItem(
        modifier = Modifier.clickable { onClick() },
        leadingContent = icon,
        headlineContent = { Text(title) },
        supportingContent = { subtitle?.let { Text(it) } }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun <T : Enum<T>> EnumRow(
    icon: @Composable (() -> Unit)? = null,
    title: String,
    current: T,
    all: Array<T>,
    onSelect: (T) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    ListItem(
        modifier = Modifier.clickable { expanded = true },
        leadingContent = icon,
        headlineContent = { Text(title) },
        supportingContent = { Text(current.name.lowercase().replaceFirstChar { it.uppercase() }) },
        trailingContent = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) }
    )

    DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
        all.forEach { option ->
            DropdownMenuItem(
                text = { Text(option.name.lowercase().replaceFirstChar { it.uppercase() }) },
                onClick = {
                    onSelect(option)
                    expanded = false
                }
            )
        }
    }
}
