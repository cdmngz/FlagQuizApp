package com.example.flagquizapp.ui.components.daily

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.AsyncImage
import com.example.flagquizapp.model.Country

@Composable
fun FlagBoxGrid(
    country: Country,
    revealedBoxes: List<Int>,
    loader: ImageLoader,
    rows: Int = 2,
    columns: Int = 3,
) {
    Box(Modifier) {
        // ─── 1) Full-flag background ───────────────────────────────
        AsyncImage(
            model            = country.svgUrl,
            imageLoader      = loader,
            contentDescription = country.name,
            modifier         = Modifier.matchParentSize(),
            contentScale     = ContentScale.Crop
        )

        // ─── 2) 2×3 blue overlay ──────────────────────────────────
        Column(Modifier.matchParentSize(), verticalArrangement = Arrangement.spacedBy(4.dp)) {
        for (row in 0 until rows) {
                Row(
                    modifier              = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    for (col in 0 until columns) {
                        val index = row * columns + col
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight()
                                .background(
                                    // if this index is “revealed”, make it transparent
                                    if (index in revealedBoxes) Color.Transparent
                                    // otherwise cover it in blue
                                    else Color(0xFF007BFF)
                                )
                        )
                    }
                }
            }
        }
    }
}
