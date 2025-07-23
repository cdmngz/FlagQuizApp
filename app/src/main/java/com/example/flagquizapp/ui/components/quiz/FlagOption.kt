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
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.example.flagquizapp.model.Country

@Composable
fun FlagOption(
    country: Country,
    correct: Country,
    answered: Boolean,
    onClick: () -> Unit
) {
    val context = LocalContext.current

    val imageLoader = ImageLoader.Builder(context)
        .components { add(SvgDecoder.Factory()) }
        .build()

    Box(modifier = Modifier) {
        Box(
            modifier = Modifier
                .size(160.dp)
                .clip(RoundedCornerShape(8.dp))
                .border(
                    BorderStroke(
                        width = 4.dp,
                        color = when {
                            !answered           -> Color.Transparent
                            country == correct  -> Color.Green
                            else                -> Color.Red
                        }
                    ),
                    shape = RoundedCornerShape(8.dp)
                )
                .clickable(enabled = !answered, onClick = onClick)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(context)
                    .data(country.svgUrl)
                    .crossfade(true)
                    .build(),
                imageLoader = imageLoader,
                contentDescription = country.name,
                modifier = Modifier.matchParentSize()
            )

            if (answered) {
                Text(
                    text = country.name,
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.White,
                    maxLines = 2,
                    softWrap = true,
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .background(
                            Color.Black.copy(alpha = 0.4f),
                            shape = RoundedCornerShape(4.dp)
                        )
                        .padding(6.dp)
                )
            }
        }
    }
}
