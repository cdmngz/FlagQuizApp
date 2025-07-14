package com.example.flagquizapp.data

import com.example.flagquizapp.model.Country
import com.example.flagquizapp.model.Continents

fun getWorldCountries(): List<Country> = listOf(
    // ─── Africa (8) ───────────────────────────────────────────────────────────────
    Country(
        name      = "Nigeria",
        svgUrl    = "https://upload.wikimedia.org/wikipedia/commons/7/79/Flag_of_Nigeria.svg",
        continent = Continents.AFRICA
    ),
    Country(
        name      = "Egypt",
        svgUrl    = "https://upload.wikimedia.org/wikipedia/commons/f/fe/Flag_of_Egypt.svg",
        continent = Continents.AFRICA
    ),
    Country(
        name      = "South Africa",
        svgUrl    = "https://upload.wikimedia.org/wikipedia/commons/a/af/Flag_of_South_Africa.svg",
        continent = Continents.AFRICA
    ),
    Country(
        name      = "Kenya",
        svgUrl    = "https://upload.wikimedia.org/wikipedia/commons/4/49/Flag_of_Kenya.svg",
        continent = Continents.AFRICA
    ),
    Country(
        name      = "Ghana",
        svgUrl    = "https://upload.wikimedia.org/wikipedia/commons/1/19/Flag_of_Ghana.svg",
        continent = Continents.AFRICA
    ),
    Country(
        name      = "Ethiopia",
        svgUrl    = "https://upload.wikimedia.org/wikipedia/commons/7/71/Flag_of_Ethiopia.svg",
        continent = Continents.AFRICA
    ),
    Country(
        name      = "Morocco",
        svgUrl    = "https://upload.wikimedia.org/wikipedia/commons/2/2c/Flag_of_Morocco.svg",
        continent = Continents.AFRICA
    ),
    Country(
        name      = "Tanzania",
        svgUrl    = "https://upload.wikimedia.org/wikipedia/commons/3/38/Flag_of_Tanzania.svg",
        continent = Continents.AFRICA
    ),

    // ─── Asia (8) ─────────────────────────────────────────────────────────────────
    Country(
        name      = "China",
        svgUrl    = "https://upload.wikimedia.org/wikipedia/commons/f/fa/Flag_of_the_People%27s_Republic_of_China.svg",
        continent = Continents.ASIA
    ),
    Country(
        name      = "Japan",
        svgUrl    = "https://upload.wikimedia.org/wikipedia/en/9/9e/Flag_of_Japan.svg",
        continent = Continents.ASIA
    ),
    Country(
        name      = "India",
        svgUrl    = "https://upload.wikimedia.org/wikipedia/en/4/41/Flag_of_India.svg",
        continent = Continents.ASIA
    ),
    Country(
        name      = "South Korea",
        svgUrl    = "https://upload.wikimedia.org/wikipedia/commons/0/09/Flag_of_South_Korea.svg",
        continent = Continents.ASIA
    ),
    Country(
        name      = "Saudi Arabia",
        svgUrl    = "https://upload.wikimedia.org/wikipedia/commons/0/0d/Flag_of_Saudi_Arabia.svg",
        continent = Continents.ASIA
    ),
    Country(
        name      = "Indonesia",
        svgUrl    = "https://upload.wikimedia.org/wikipedia/commons/9/9f/Flag_of_Indonesia.svg",
        continent = Continents.ASIA
    ),
    Country(
        name      = "Thailand",
        svgUrl    = "https://upload.wikimedia.org/wikipedia/commons/a/a9/Flag_of_Thailand.svg",
        continent = Continents.ASIA
    ),
    Country(
        name      = "Vietnam",
        svgUrl    = "https://upload.wikimedia.org/wikipedia/commons/2/21/Flag_of_Vietnam.svg",
        continent = Continents.ASIA
    ),

    // ─── Europe (8) ────────────────────────────────────────────────────────────────
    Country(
        name      = "Spain",
        svgUrl    = "https://upload.wikimedia.org/wikipedia/en/9/9a/Flag_of_Spain.svg",
        continent = Continents.EUROPE
    ),
    Country(
        name      = "France",
        svgUrl    = "https://upload.wikimedia.org/wikipedia/en/c/c3/Flag_of_France.svg",
        continent = Continents.EUROPE
    ),
    Country(
        name      = "Germany",
        svgUrl    = "https://upload.wikimedia.org/wikipedia/en/b/ba/Flag_of_Germany.svg",
        continent = Continents.EUROPE
    ),
    Country(
        name      = "Italy",
        svgUrl    = "https://upload.wikimedia.org/wikipedia/en/0/03/Flag_of_Italy.svg",
        continent = Continents.EUROPE
    ),
    Country(
        name      = "United Kingdom",
        svgUrl    = "https://upload.wikimedia.org/wikipedia/en/a/ae/Flag_of_the_United_Kingdom.svg",
        continent = Continents.EUROPE
    ),
    Country(
        name      = "Russia",
        svgUrl    = "https://upload.wikimedia.org/wikipedia/en/f/f3/Flag_of_Russia.svg",
        continent = Continents.EUROPE
    ),
    Country(
        name      = "Netherlands",
        svgUrl    = "https://upload.wikimedia.org/wikipedia/commons/2/20/Flag_of_the_Netherlands.svg",
        continent = Continents.EUROPE
    ),
    Country(
        name      = "Sweden",
        svgUrl    = "https://upload.wikimedia.org/wikipedia/en/4/4c/Flag_of_Sweden.svg",
        continent = Continents.EUROPE
    ),

    // ─── North America (8) ─────────────────────────────────────────────────────────
    Country(
        name      = "United States",
        svgUrl    = "https://upload.wikimedia.org/wikipedia/en/a/a4/Flag_of_the_United_States.svg",
        continent = Continents.NORTH_AMERICA
    ),
    Country(
        name      = "Canada",
        svgUrl    = "https://upload.wikimedia.org/wikipedia/commons/c/cf/Flag_of_Canada.svg",
        continent = Continents.NORTH_AMERICA
    ),
    Country(
        name      = "Mexico",
        svgUrl    = "https://upload.wikimedia.org/wikipedia/commons/f/fc/Flag_of_Mexico.svg",
        continent = Continents.NORTH_AMERICA
    ),
    Country(
        name      = "Guatemala",
        svgUrl    = "https://upload.wikimedia.org/wikipedia/commons/e/ec/Flag_of_Guatemala.svg",
        continent = Continents.NORTH_AMERICA
    ),
    Country(
        name      = "Cuba",
        svgUrl    = "https://upload.wikimedia.org/wikipedia/commons/b/bd/Flag_of_Cuba.svg",
        continent = Continents.NORTH_AMERICA
    ),
    Country(
        name      = "Jamaica",
        svgUrl    = "https://upload.wikimedia.org/wikipedia/commons/0/0a/Flag_of_Jamaica.svg",
        continent = Continents.NORTH_AMERICA
    ),
    Country(
        name      = "Panama",
        svgUrl    = "https://upload.wikimedia.org/wikipedia/commons/a/ab/Flag_of_Panama.svg",
        continent = Continents.NORTH_AMERICA
    ),
    Country(
        name      = "Bahamas",
        svgUrl    = "https://upload.wikimedia.org/wikipedia/commons/9/93/Flag_of_the_Bahamas.svg",
        continent = Continents.NORTH_AMERICA
    ),

    // ─── South America (8) ─────────────────────────────────────────────────────────
    Country(
        name      = "Brazil",
        svgUrl    = "https://upload.wikimedia.org/wikipedia/en/0/05/Flag_of_Brazil.svg",
        continent = Continents.SOUTH_AMERICA
    ),
    Country(
        name      = "Argentina",
        svgUrl    = "https://upload.wikimedia.org/wikipedia/commons/1/1a/Flag_of_Argentina.svg",
        continent = Continents.SOUTH_AMERICA
    ),
    Country(
        name      = "Chile",
        svgUrl    = "https://upload.wikimedia.org/wikipedia/commons/7/78/Flag_of_Chile.svg",
        continent = Continents.SOUTH_AMERICA
    ),
    Country(
        name      = "Colombia",
        svgUrl    = "https://upload.wikimedia.org/wikipedia/commons/2/21/Flag_of_Colombia.svg",
        continent = Continents.SOUTH_AMERICA
    ),
    Country(
        name      = "Peru",
        svgUrl    = "https://upload.wikimedia.org/wikipedia/commons/d/df/Flag_of_Peru.svg",
        continent = Continents.SOUTH_AMERICA
    ),
    Country(
        name      = "Venezuela",
        svgUrl    = "https://upload.wikimedia.org/wikipedia/commons/0/06/Flag_of_Venezuela.svg",
        continent = Continents.SOUTH_AMERICA
    ),
    Country(
        name      = "Ecuador",
        svgUrl    = "https://upload.wikimedia.org/wikipedia/commons/e/e8/Flag_of_Ecuador.svg",
        continent = Continents.SOUTH_AMERICA
    ),
    Country(
        name      = "Bolivia",
        svgUrl    = "https://upload.wikimedia.org/wikipedia/commons/d/de/Flag_of_Bolivia.svg",
        continent = Continents.SOUTH_AMERICA
    ),

    // ─── Oceania (8) ───────────────────────────────────────────────────────────────
    Country(
        name      = "Australia",
        svgUrl    = "https://upload.wikimedia.org/wikipedia/en/b/b9/Flag_of_Australia.svg",
        continent = Continents.OCEANIA
    ),
    Country(
        name      = "New Zealand",
        svgUrl    = "https://upload.wikimedia.org/wikipedia/commons/3/3e/Flag_of_New_Zealand.svg",
        continent = Continents.OCEANIA
    ),
    Country(
        name      = "Papua New Guinea",
        svgUrl    = "https://upload.wikimedia.org/wikipedia/commons/e/e8/Flag_of_Papua_New_Guinea.svg",
        continent = Continents.OCEANIA
    ),
    Country(
        name      = "Fiji",
        svgUrl    = "https://upload.wikimedia.org/wikipedia/commons/b/ba/Flag_of_Fiji.svg",
        continent = Continents.OCEANIA
    ),
    Country(
        name      = "Samoa",
        svgUrl    = "https://upload.wikimedia.org/wikipedia/commons/3/31/Flag_of_Samoa.svg",
        continent = Continents.OCEANIA
    ),
    Country(
        name      = "Tonga",
        svgUrl    = "https://upload.wikimedia.org/wikipedia/commons/9/9a/Flag_of_Tonga.svg",
        continent = Continents.OCEANIA
    ),
    Country(
        name      = "Vanuatu",
        svgUrl    = "https://upload.wikimedia.org/wikipedia/commons/e/e3/Flag_of_Vanuatu.svg",
        continent = Continents.OCEANIA
    ),
    Country(
        name      = "Solomon Islands",
        svgUrl    = "https://upload.wikimedia.org/wikipedia/commons/7/74/Flag_of_the_Solomon_Islands.svg",
        continent = Continents.OCEANIA
    ),

    // ─── Antarctica (2) ─────────────────────────────────────────────────────────────
    Country(
        name      = "Antarctica",
        svgUrl    = "https://upload.wikimedia.org/wikipedia/commons/6/6b/Flag_of_Antarctica.svg",
        continent = Continents.ANTARCTICA
    ),
    Country(
        name      = "Antarctic Treaty",
        svgUrl    = "https://upload.wikimedia.org/wikipedia/commons/1/1f/Flag_of_the_Antarctic_Treaty.svg",
        continent = Continents.ANTARCTICA
    )
)
