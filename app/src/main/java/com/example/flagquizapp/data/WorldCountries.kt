package com.example.flagquizapp.data

import com.example.flagquizapp.model.Country
import com.example.flagquizapp.model.Continent
import com.example.flagquizapp.model.Subregion

fun getWorldCountries(): List<Country> = listOf(
    Country(
        name = "Nigeria",
        svgUrl = "https://upload.wikimedia.org/wikipedia/commons/7/79/Flag_of_Nigeria.svg",
        continent = Continent.AFRICA,
        subregion = Subregion.WESTERN_AFRICA,
        population = 206139589,
        capital = "Abuja"
    ),
    Country(
        name = "Egypt",
        svgUrl = "https://upload.wikimedia.org/wikipedia/commons/f/fe/Flag_of_Egypt.svg",
        continent = Continent.AFRICA,
        subregion = Subregion.NORTHERN_AFRICA,
        population = 102334404,
        capital = "Cairo"
    ),
    Country(
        name = "South Africa",
        svgUrl = "https://upload.wikimedia.org/wikipedia/commons/a/af/Flag_of_South_Africa.svg",
        continent = Continent.AFRICA,
        subregion = Subregion.SOUTHERN_AFRICA,
        population = 59308690,
        capital = "Pretoria"
    ),
    Country(
        name = "Kenya",
        svgUrl = "https://upload.wikimedia.org/wikipedia/commons/4/49/Flag_of_Kenya.svg",
        continent = Continent.AFRICA,
        subregion = Subregion.EASTERN_AFRICA,
        population = 53771296,
        capital = "Nairobi"
    ),
    Country(
        name = "Ghana",
        svgUrl = "https://upload.wikimedia.org/wikipedia/commons/1/19/Flag_of_Ghana.svg",
        continent = Continent.AFRICA,
        subregion = Subregion.WESTERN_AFRICA,
        population = 31072940,
        capital = "Accra"
    ),
    Country(
        name = "Ethiopia",
        svgUrl = "https://upload.wikimedia.org/wikipedia/commons/7/71/Flag_of_Ethiopia.svg",
        continent = Continent.AFRICA,
        subregion = Subregion.EASTERN_AFRICA,
        population = 114963588,
        capital = "Addis Ababa"
    ),
    Country(
        name = "Morocco",
        svgUrl = "https://upload.wikimedia.org/wikipedia/commons/2/2c/Flag_of_Morocco.svg",
        continent = Continent.AFRICA,
        subregion = Subregion.NORTHERN_AFRICA,
        population = 36910560,
        capital = "Rabat"
    ),
    Country(
        name = "Tanzania",
        svgUrl = "https://upload.wikimedia.org/wikipedia/commons/3/38/Flag_of_Tanzania.svg",
        continent = Continent.AFRICA,
        subregion = Subregion.EASTERN_AFRICA,
        population = 59734218,
        capital = "Dodoma"
    ),

    Country(
        name = "China",
        svgUrl = "https://upload.wikimedia.org/wikipedia/commons/f/fa/Flag_of_the_People%27s_Republic_of_China.svg",
        continent = Continent.ASIA,
        subregion = Subregion.EAST_ASIA,
        population = 1439323776,
        capital = "Beijing"
    ),
    Country(
        name = "Japan",
        svgUrl = "https://upload.wikimedia.org/wikipedia/en/9/9e/Flag_of_Japan.svg",
        continent = Continent.ASIA,
        subregion = Subregion.EAST_ASIA,
        population = 126476461,
        capital = "Tokyo"
    ),
    Country(
        name = "India",
        svgUrl = "https://upload.wikimedia.org/wikipedia/en/4/41/Flag_of_India.svg",
        continent = Continent.ASIA,
        subregion = Subregion.SOUTH_ASIA,
        population = 1380004385,
        capital = "New Delhi"
    ),
    Country(
        name = "South Korea",
        svgUrl = "https://upload.wikimedia.org/wikipedia/commons/0/09/Flag_of_South_Korea.svg",
        continent = Continent.ASIA,
        subregion = Subregion.EAST_ASIA,
        population = 51269185,
        capital = "Seoul"
    ),
    Country(
        name = "Saudi Arabia",
        svgUrl = "https://upload.wikimedia.org/wikipedia/commons/0/0d/Flag_of_Saudi_Arabia.svg",
        continent = Continent.ASIA,
        subregion = Subregion.WEST_ASIA,
        population = 34813871,
        capital = "Riyadh"
    ),
    Country(
        name = "Indonesia",
        svgUrl = "https://upload.wikimedia.org/wikipedia/commons/9/9f/Flag_of_Indonesia.svg",
        continent = Continent.ASIA,
        subregion = Subregion.SOUTHEAST_ASIA,
        population = 273523615,
        capital = "Jakarta"
    ),
    Country(
        name = "Thailand",
        svgUrl = "https://upload.wikimedia.org/wikipedia/commons/a/a9/Flag_of_Thailand.svg",
        continent = Continent.ASIA,
        subregion = Subregion.SOUTHEAST_ASIA,
        population = 69799978,
        capital = "Bangkok"
    ),
    Country(
        name = "Vietnam",
        svgUrl = "https://upload.wikimedia.org/wikipedia/commons/2/21/Flag_of_Vietnam.svg",
        continent = Continent.ASIA,
        subregion = Subregion.SOUTHEAST_ASIA,
        population = 97338579,
        capital = "Hanoi"
    ),

    Country(
        name = "Spain",
        svgUrl = "https://upload.wikimedia.org/wikipedia/en/9/9a/Flag_of_Spain.svg",
        continent = Continent.EUROPE,
        subregion = Subregion.SOUTHERN_EUROPE,
        population = 46754778,
        capital = "Madrid"
    ),
    Country(
        name = "France",
        svgUrl = "https://upload.wikimedia.org/wikipedia/en/c/c3/Flag_of_France.svg",
        continent = Continent.EUROPE,
        subregion = Subregion.WESTERN_EUROPE,
        population = 65273511,
        capital = "Paris"
    ),
    Country(
        name = "Germany",
        svgUrl = "https://upload.wikimedia.org/wikipedia/en/b/ba/Flag_of_Germany.svg",
        continent = Continent.EUROPE,
        subregion = Subregion.WESTERN_EUROPE,
        population = 83783942,
        capital = "Berlin"
    ),
    Country(
        name = "Italy",
        svgUrl = "https://upload.wikimedia.org/wikipedia/en/0/03/Flag_of_Italy.svg",
        continent = Continent.EUROPE,
        subregion = Subregion.SOUTHERN_EUROPE,
        population = 60461826,
        capital = "Rome"
    ),
    Country(
        name = "United Kingdom",
        svgUrl = "https://upload.wikimedia.org/wikipedia/en/a/ae/Flag_of_the_United_Kingdom.svg",
        continent = Continent.EUROPE,
        subregion = Subregion.NORTHERN_EUROPE,
        population = 67886011,
        capital = "London"
    ),
    Country(
        name = "Russia",
        svgUrl = "https://upload.wikimedia.org/wikipedia/en/f/f3/Flag_of_Russia.svg",
        continent = Continent.EUROPE,
        subregion = Subregion.EASTERN_EUROPE,
        population = 145934462,
        capital = "Moscow"
    ),
    Country(
        name = "Netherlands",
        svgUrl = "https://upload.wikimedia.org/wikipedia/commons/2/20/Flag_of_the_Netherlands.svg",
        continent = Continent.EUROPE,
        subregion = Subregion.WESTERN_EUROPE,
        population = 17134872,
        capital = "Amsterdam"
    ),
    Country(
        name = "Sweden",
        svgUrl = "https://upload.wikimedia.org/wikipedia/en/4/4c/Flag_of_Sweden.svg",
        continent = Continent.EUROPE,
        subregion = Subregion.NORTHERN_EUROPE,
        population = 10099265,
        capital = "Stockholm"
    ),

    Country(
        name = "United States",
        svgUrl = "https://upload.wikimedia.org/wikipedia/en/a/a4/Flag_of_the_United_States.svg",
        continent = Continent.AMERICA,
        subregion = Subregion.NORTH_AMERICA,
        population = 331002651,
        capital = "Washington, D.C."
    ),
    Country(
        name = "Canada",
        svgUrl = "https://upload.wikimedia.org/wikipedia/commons/c/cf/Flag_of_Canada.svg",
        continent = Continent.AMERICA,
        subregion = Subregion.NORTH_AMERICA,
        population = 37742154,
        capital = "Ottawa"
    ),
    Country(
        name = "Mexico",
        svgUrl = "https://upload.wikimedia.org/wikipedia/commons/f/fc/Flag_of_Mexico.svg",
        continent = Continent.AMERICA,
        subregion = Subregion.CENTRAL_AMERICA,
        population = 128932753,
        capital = "Mexico City"
    ),
    Country(
        name = "Guatemala",
        svgUrl = "https://upload.wikimedia.org/wikipedia/commons/e/ec/Flag_of_Guatemala.svg",
        continent = Continent.AMERICA,
        subregion = Subregion.CENTRAL_AMERICA,
        population = 17915568,
        capital = "Guatemala City"
    ),
    Country(
        name = "Cuba",
        svgUrl = "https://upload.wikimedia.org/wikipedia/commons/b/bd/Flag_of_Cuba.svg",
        continent = Continent.AMERICA,
        subregion = Subregion.CARIBBEAN,
        population = 11326616,
        capital = "Havana"
    ),
    Country(
        name = "Jamaica",
        svgUrl = "https://upload.wikimedia.org/wikipedia/commons/0/0a/Flag_of_Jamaica.svg",
        continent = Continent.AMERICA,
        subregion = Subregion.CARIBBEAN,
        population = 2961167,
        capital = "Kingston"
    ),
    Country(
        name = "Panama",
        svgUrl = "https://upload.wikimedia.org/wikipedia/commons/a/ab/Flag_of_Panama.svg",
        continent = Continent.AMERICA,
        subregion = Subregion.CENTRAL_AMERICA,
        population = 4314767,
        capital = "Panama City"
    ),
    Country(
        name = "Bahamas",
        svgUrl = "https://upload.wikimedia.org/wikipedia/commons/9/93/Flag_of_the_Bahamas.svg",
        continent = Continent.AMERICA,
        subregion = Subregion.CARIBBEAN,
        population = 393244,
        capital = "Nassau"
    ),
    Country(
        name = "Brazil",
        svgUrl = "https://upload.wikimedia.org/wikipedia/en/0/05/Flag_of_Brazil.svg",
        continent = Continent.AMERICA,
        subregion = Subregion.SOUTH_AMERICA,
        population = 212559417,
        capital = "Brasília"
    ),
    Country(
        name = "Argentina",
        svgUrl = "https://upload.wikimedia.org/wikipedia/commons/1/1a/Flag_of_Argentina.svg",
        continent = Continent.AMERICA,
        subregion = Subregion.SOUTH_AMERICA,
        population = 45195774,
        capital = "Buenos Aires"
    ),
    Country(
        name = "Chile",
        svgUrl = "https://upload.wikimedia.org/wikipedia/commons/7/78/Flag_of_Chile.svg",
        continent = Continent.AMERICA,
        subregion = Subregion.SOUTH_AMERICA,
        population = 19116201,
        capital = "Santiago"
    ),
    Country(
        name = "Colombia",
        svgUrl = "https://upload.wikimedia.org/wikipedia/commons/2/21/Flag_of_Colombia.svg",
        continent = Continent.AMERICA,
        subregion = Subregion.SOUTH_AMERICA,
        population = 50882891,
        capital = "Bogotá"
    ),
    Country(
        name = "Peru",
        svgUrl = "https://upload.wikimedia.org/wikipedia/commons/d/df/Flag_of_Peru.svg",
        continent = Continent.AMERICA,
        subregion = Subregion.SOUTH_AMERICA,
        population = 32971854,
        capital = "Lima"
    ),
    Country(
        name = "Venezuela",
        svgUrl = "https://upload.wikimedia.org/wikipedia/commons/0/06/Flag_of_Venezuela.svg",
        continent = Continent.AMERICA,
        subregion = Subregion.SOUTH_AMERICA,
        population = 28435940,
        capital = "Caracas"
    ),
    Country(
        name = "Ecuador",
        svgUrl = "https://upload.wikimedia.org/wikipedia/commons/e/e8/Flag_of_Ecuador.svg",
        continent = Continent.AMERICA,
        subregion = Subregion.SOUTH_AMERICA,
        population = 17643054,
        capital = "Quito"
    ),
    Country(
        name = "Bolivia",
        svgUrl = "https://upload.wikimedia.org/wikipedia/commons/d/de/Flag_of_Bolivia.svg",
        continent = Continent.AMERICA,
        subregion = Subregion.SOUTH_AMERICA,
        population = 11673021,
        capital = "La Paz"
    ),

    Country(
        name = "Australia",
        svgUrl = "https://upload.wikimedia.org/wikipedia/en/b/b9/Flag_of_Australia.svg",
        continent = Continent.OCEANIA,
        subregion = Subregion.AUSTRALIA_AND_NEW_ZEALAND,
        population = 25499884,
        capital = "Canberra"
    ),
    Country(
        name = "New Zealand",
        svgUrl = "https://upload.wikimedia.org/wikipedia/commons/3/3e/Flag_of_New_Zealand.svg",
        continent = Continent.OCEANIA,
        subregion = Subregion.AUSTRALIA_AND_NEW_ZEALAND,
        population = 4822233,
        capital = "Wellington"
    ),
    Country(
        name = "Papua New Guinea",
        svgUrl = "https://upload.wikimedia.org/wikipedia/commons/e/e8/Flag_of_Papua_New_Guinea.svg",
        continent = Continent.OCEANIA,
        subregion = Subregion.MELANESIA,
        population = 8947024,
        capital = "Port Moresby"
    ),
    Country(
        name = "Fiji",
        svgUrl = "https://upload.wikimedia.org/wikipedia/commons/b/ba/Flag_of_Fiji.svg",
        continent = Continent.OCEANIA,
        subregion = Subregion.MELANESIA,
        population = 896445,
        capital = "Suva"
    ),
    Country(
        name = "Samoa",
        svgUrl = "https://upload.wikimedia.org/wikipedia/commons/3/31/Flag_of_Samoa.svg",
        continent = Continent.OCEANIA,
        subregion = Subregion.POLYNESIA,
        population = 198414,
        capital = "Apia"
    ),
    Country(
        name = "Tonga",
        svgUrl = "https://upload.wikimedia.org/wikipedia/commons/9/9a/Flag_of_Tonga.svg",
        continent = Continent.OCEANIA,
        subregion = Subregion.POLYNESIA,
        population = 105695,
        capital = "Nuku'alofa"
    ),
    Country(
        name = "Vanuatu",
        svgUrl = "https://upload.wikimedia.org/wikipedia/commons/e/e3/Flag_of_Vanuatu.svg",
        continent = Continent.OCEANIA,
        subregion = Subregion.MELANESIA,
        population = 307145,
        capital = "Port Vila"
    ),
    Country(
        name = "Solomon Islands",
        svgUrl = "https://upload.wikimedia.org/wikipedia/commons/7/74/Flag_of_the_Solomon_Islands.svg",
        continent = Continent.OCEANIA,
        subregion = Subregion.MELANESIA,
        population = 686884,
        capital = "Honiara"
    )
)
