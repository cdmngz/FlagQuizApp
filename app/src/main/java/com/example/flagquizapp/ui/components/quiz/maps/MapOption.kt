package com.example.flagquizapp.ui.components.quiz.maps

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.flagquizapp.model.Country

@Composable
fun MapOption(
    country: Country,
    correct: Country,
    answered: Boolean,
    onClick: () -> Unit,
    geoJson: String
) {
    // each tile’s own zoom & pan state
    var zoom by remember(country) { mutableStateOf(1f) }
    var offset by remember(country) { mutableStateOf(Offset.Zero) }
    val minZoom = 0.1f
    val maxZoom = 5f

    // soft light-blue sea
    val seaColor = Color(0xFFB3E5FC)

    Box(
        modifier = Modifier
            .size(120.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(seaColor)
            .border(
                BorderStroke(
                    width = if (!answered) 2.dp else 4.dp,
                    color = when {
                        !answered -> Color.Gray
                        country == correct -> Color.Green
                        else -> Color.Red
                    }
                ),
                shape = RoundedCornerShape(8.dp)
            )
            .clickable(enabled = !answered) { onClick() }
    ) {
        CountryShapeCanvas(
            geoJson = geoJson,
            highlight = country.code,
            zoom = zoom,
            offset = offset,
            onZoomChange = { zoom = it },
            onPan = { delta -> offset += delta },
            modifier = Modifier.matchParentSize()
        )

        // zoom controls
        Column(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(4.dp),
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            IconButton(
                onClick = { zoom = (zoom * 2f).coerceAtMost(maxZoom) },
                modifier = Modifier.size(24.dp)
            ) {
                Icon(
                    Icons.Default.KeyboardArrowUp,
                    contentDescription = "Zoom In",
                    tint = Color.Black
                )
            }
            IconButton(
                onClick = { zoom = (zoom / 2f).coerceAtLeast(minZoom) },
                modifier = Modifier.size(24.dp)
            ) {
                Icon(
                    Icons.Default.KeyboardArrowDown,
                    contentDescription = "Zoom Out",
                    tint = Color.Black
                )
            }
        }
    }
}
