package com.example.flagquizapp.model

data class Country(
    val name: String,
    val svgUrl: String,
    val continent: Continents,
    val population: Int,
    val capital: String
)
