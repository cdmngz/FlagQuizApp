package com.example.flagquizapp.data

import com.example.flagquizapp.model.Country

fun getWorldCountries(): List<Country> = listOf(
    Country("Spain", "https://upload.wikimedia.org/wikipedia/en/9/9a/Flag_of_Spain.svg"),
    Country("France", "https://upload.wikimedia.org/wikipedia/en/c/c3/Flag_of_France.svg"),
    Country("Germany", "https://upload.wikimedia.org/wikipedia/en/b/ba/Flag_of_Germany.svg"),
    Country("Brazil", "https://upload.wikimedia.org/wikipedia/en/0/05/Flag_of_Brazil.svg"),
    Country("Japan", "https://upload.wikimedia.org/wikipedia/en/9/9e/Flag_of_Japan.svg"),
    Country("Italy", "https://upload.wikimedia.org/wikipedia/en/0/03/Flag_of_Italy.svg")
)
