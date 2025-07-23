package com.example.flagquizapp.ui.components.common

import androidx.annotation.DrawableRes
import androidx.annotation.RawRes
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest

// --- Progress ring modifier --------------------------------------------------

fun Modifier.progressRing(
    progress: Float,
    ringColor: Color,
    trackColor: Color,
    strokeWidth: Dp
) = drawBehind {
    val stroke = Stroke(width = strokeWidth.toPx(), cap = StrokeCap.Round)
    val diameter = size.minDimension
    val topLeft = androidx.compose.ui.geometry.Offset(
        (size.width - diameter) / 2f,
        (size.height - diameter) / 2f
    )
    val arcSize = androidx.compose.ui.geometry.Size(diameter, diameter)

    // track
    drawArc(
        color = trackColor,
        startAngle = -90f,
        sweepAngle = 360f,
        useCenter = false,
        topLeft = topLeft,
        size = arcSize,
        style = stroke
    )

    // progress
    drawArc(
        color = ringColor,
        startAngle = -90f,
        sweepAngle = 360f * progress.coerceIn(0f, 1f),
        useCenter = false,
        topLeft = topLeft,
        size = arcSize,
        style = stroke
    )
}

// --- Core circle that draws the SVG/vector inside ----------------------------

@Composable
fun CircularSvgOption(
    text: String? = null,
    svgUrl: String? = null,
    @RawRes svgRes: Int? = null,
    @DrawableRes drawableRes: Int? = null,
    progress: Float,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    size: Dp = 96.dp,
    strokeWidth: Dp = 6.dp,
    showText: Boolean = false
) {
    val context = LocalContext.current
    val imageLoader = remember {
        ImageLoader.Builder(context)
            .components { add(SvgDecoder.Factory()) }
            .build()
    }

    val animatedProgress by animateFloatAsState(progress, label = "ringProgress")
    val targetColor =
        if (animatedProgress >= 1f) Color(0xFFFFD700) else MaterialTheme.colorScheme.primary
    val ringColor by animateColorAsState(targetColor, label = "ringColor")

    Box(
        modifier = modifier
            .size(size)
            .clip(CircleShape)
            .clickable(onClick = onClick)
            .progressRing(
                progress = animatedProgress,
                ringColor = ringColor,
                trackColor = MaterialTheme.colorScheme.surfaceVariant,
                strokeWidth = strokeWidth
            ),
        contentAlignment = Alignment.Center
    ) {
        when {
            svgUrl != null -> {
                AsyncImage(
                    model = ImageRequest.Builder(context).data(svgUrl).crossfade(true).build(),
                    imageLoader = imageLoader,
                    contentDescription = text,
                    modifier = Modifier.fillMaxSize(0.65f),
                    contentScale = ContentScale.Fit
                )
            }

            svgRes != null -> {
                AsyncImage(
                    model = ImageRequest.Builder(context).data(svgRes).build(),
                    imageLoader = imageLoader,
                    contentDescription = text,
                    modifier = Modifier.fillMaxSize(0.65f),
                    contentScale = ContentScale.Fit
                )
            }

            drawableRes != null -> {
                Image(
                    painter = painterResource(drawableRes),
                    contentDescription = text,
                    modifier = Modifier.fillMaxSize(0.65f),
                    contentScale = ContentScale.Fit
                )
            }
        }

        if (showText && text != null) {
            Text(
                text = text,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

// --- Wrapper that puts the label UNDER the circle ----------------------------

@Composable
fun CircularImageWithLabel(
    label: String,
    progress: Float,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    @RawRes svgRes: Int? = null,
    @DrawableRes drawableRes: Int? = null,
    svgUrl: String? = null,
    circleSize: Dp = 96.dp,
    strokeWidth: Dp = 6.dp
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = modifier) {
        CircularSvgOption(
            text = null,
            svgUrl = svgUrl,
            svgRes = svgRes,
            drawableRes = drawableRes,
            progress = progress,
            onClick = onClick,
            size = circleSize,
            strokeWidth = strokeWidth,
            showText = false
        )
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier.padding(top = 6.dp)
        )
    }
}
