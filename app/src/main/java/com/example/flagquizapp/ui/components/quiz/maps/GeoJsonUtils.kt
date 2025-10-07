package com.example.flagquizapp.ui.components.quiz.maps

import org.json.JSONObject
import kotlin.math.abs

object GeoJsonUtils {
    fun polygonArea(ring: List<Pair<Double, Double>>): Double {
        var sum = 0.0
        val n = ring.size
        for (i in 0 until n) {
            val (x1, y1) = ring[i]; val (x2, y2) = ring[(i + 1) % n]
            sum += x1 * y2 - x2 * y1
        }
        return abs(sum) * 0.5
    }

    fun extractAllPolygons(geometry: JSONObject) =
        mutableListOf<List<Pair<Double, Double>>>().apply {
            val type = geometry.getString("type")
            val coords = geometry.getJSONArray("coordinates")
            fun ring(arr: org.json.JSONArray) = List(arr.length()) {
                val p = arr.getJSONArray(it); p.getDouble(0) to p.getDouble(1)
            }
            when (type) {
                "Polygon" -> add(ring(coords.getJSONArray(0)))
                "MultiPolygon" ->
                    for (i in 0 until coords.length())
                        add(ring(coords.getJSONArray(i).getJSONArray(0)))
            }
        }.let { rings ->
            if (rings.size > 1) listOf(rings.maxByOrNull { polygonArea(it) }!!) else rings
        }
}
