package com.example.flagquizapp.model

data class Country(
    val code: String,
    val name: String,
    val svgUrl: String,
    val mapUrl: String = "",
    val continent: Continent,
    val population: Int,
    val capital: String,
    val subregion: Subregion
)
