package com.example.flagquizapp.ui.components.quiz

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.flagquizapp.model.Country

@Composable
fun MapOption(
    country: Country,
    correct: Country,
    answered: Boolean,
    onClick: () -> Unit
) {
    val context = LocalContext.current

    Box(modifier = Modifier) {
        Box(
            modifier = Modifier
                .size(100.dp)
                .clip(RoundedCornerShape(8.dp))
                .border(
                    BorderStroke(
                        width = if (!answered) 2.dp else 4.dp,
                        color = when {
                            !answered -> Color.Transparent
                            country == correct -> Color.Green
                            else -> Color.Red
                        }
                    ),
                    shape = RoundedCornerShape(8.dp)
                )
                .clickable(enabled = !answered, onClick = onClick)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(context)
                    .data(country.mapUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = country.name,
                modifier = Modifier.matchParentSize()
            )
        }

        if (answered) {
            Text(
                text = country.name,
                style = MaterialTheme.typography.labelSmall,
                color = Color.White,
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
