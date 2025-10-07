package com.example.flagquizapp.ui.components.quiz.maps

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import org.json.JSONObject
import kotlin.math.min

@Composable
fun CountryShapeCanvas(
    geoJson: String,
    highlight: String,
    zoom: Float,
    offset: Offset,
    onZoomChange: (Float) -> Unit,
    onPan: (Offset) -> Unit,
    highlightOnly: Boolean = false,
    modifier: Modifier = Modifier
) {
    // Parse all country polygons once
    val features = remember(geoJson) {
        JSONObject(geoJson)
            .optJSONArray("features")
            ?.let { arr ->
                List(arr.length()) { i ->
                    val feat = arr.getJSONObject(i)
                    val props = feat.getJSONObject("properties")
                    val code = props.optString("ISO_A2")
                        .takeIf(String::isNotEmpty)
                        ?: props.optString("ISO_A3")
                            .takeIf(String::isNotEmpty)
                            .orEmpty()
                    val rings = GeoJsonUtils.extractAllPolygons(feat.getJSONObject("geometry"))
                    code to rings
                }
            } ?: emptyList()
    }
    if (features.isEmpty()) return

    // Find the rings for the highlighted country
    val targetRings = features
        .firstOrNull { it.first.equals(highlight, ignoreCase = true) }
        ?.second
        ?: return

    // Pull colors from the theme *outside* the draw lambda
    val defaultFill = MaterialTheme.colorScheme.surfaceVariant
    val defaultStroke = MaterialTheme.colorScheme.outline
    val highlightFill = MaterialTheme.colorScheme.primary
    val highlightStroke = MaterialTheme.colorScheme.primaryContainer

    // Enable pan/zoom gestures only when not highlight‐only
    val canvasModifier = if (highlightOnly) {
        modifier
    } else {
        modifier.pointerInput(zoom) {
            detectTransformGestures { _, pan, zoomChange, _ ->
                onPan(Offset(pan.x, pan.y))
                if (zoomChange != 1f) {
                    onZoomChange((zoom * zoomChange).coerceIn(0.1f, 5f))
                }
            }
        }
    }

    Canvas(modifier = canvasModifier) {
        // Compute bounding box for the target country
        val allPts = targetRings.flatten()
        val xs = allPts.map { it.first }
        val ys = allPts.map { it.second }
        val minX0 = xs.minOrNull()!!
        val maxX0 = xs.maxOrNull()!!
        val minY0 = ys.minOrNull()!!
        val maxY0 = ys.maxOrNull()!!
        val padF = 0.4f
        val minX = minX0 - (maxX0 - minX0) * padF
        val maxX = maxX0 + (maxX0 - minX0) * padF
        val minY = minY0 - (maxY0 - minY0) * padF
        val maxY = maxY0 + (maxY0 - minY0) * padF

        // Calculate scale & offsets
        val geoW = maxX - minX
        val geoH = maxY - minY
        val baseScale = min(
            size.width / geoW.toFloat(),
            size.height / geoH.toFloat()
        )
        val scale = baseScale * zoom
        val xOff = (size.width - geoW.toFloat() * scale) / 2f + offset.x
        val yOff = (size.height - geoH.toFloat() * scale) / 2f + offset.y

        if (highlightOnly) {
            // Only draw the highlighted country
            targetRings.forEach { ring ->
                val path = Path().apply {
                    ring.forEachIndexed { i, (lon, lat) ->
                        val x = (lon - minX) * scale + xOff
                        val y = (maxY - lat) * scale + yOff
                        if (i == 0) moveTo(x.toFloat(), y.toFloat()) else lineTo(
                            x.toFloat(),
                            y.toFloat()
                        )
                    }
                    close()
                }
                drawPath(path, highlightFill)
                drawPath(path, highlightStroke, style = Stroke(1.dp.toPx()))
            }
        } else {
            // Draw all countries, accenting the highlighted one
            features.forEach { (code, rings) ->
                val isTarget = code.equals(highlight, ignoreCase = true)
                val fillColor = if (isTarget) highlightFill else defaultFill
                val strokeColor = if (isTarget) highlightStroke else defaultStroke

                rings.forEach { ring ->
                    val path = Path().apply {
                        ring.forEachIndexed { i, (lon, lat) ->
                            val x = (lon - minX) * scale + xOff
                            val y = (maxY - lat) * scale + yOff
                            if (i == 0) moveTo(x.toFloat(), y.toFloat()) else lineTo(
                                x.toFloat(),
                                y.toFloat()
                            )
                        }
                        close()
                    }
                    drawPath(path, fillColor)
                    drawPath(path, strokeColor, style = Stroke(1.dp.toPx()))
                }
            }
        }
    }
}
