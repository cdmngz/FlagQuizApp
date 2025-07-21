package com.example.flagquizapp.data

import com.example.flagquizapp.model.Continent
import com.example.flagquizapp.model.Country
import com.example.flagquizapp.model.Subregion

fun getWorldCountries(): List<Country> = listOf(
    // AFRICA
    Country(
        name = "Nigeria",
        svgUrl = "https://upload.wikimedia.org/wikipedia/commons/7/79/Flag_of_Nigeria.svg",
        mapUrl = "https://upload.wikimedia.org/wikipedia/commons/8/89/Nigeria_location_map.svg",
        continent = Continent.AFRICA,
        subregion = Subregion.WESTERN_AFRICA,
        population = 206139589,
        capital = "Abuja"
    ),
    Country(
        name = "Ghana",
        svgUrl = "https://upload.wikimedia.org/wikipedia/commons/1/19/Flag_of_Ghana.svg",
        mapUrl = "https://upload.wikimedia.org/wikipedia/commons/d/d0/Ghana_location_map.svg",
        continent = Continent.AFRICA,
        subregion = Subregion.WESTERN_AFRICA,
        population = 31072940,
        capital = "Accra"
    ),
    Country(
        name = "Egypt",
        svgUrl = "https://upload.wikimedia.org/wikipedia/commons/f/fe/Flag_of_Egypt.svg",
        mapUrl = "https://upload.wikimedia.org/wikipedia/commons/9/92/Egypt_location_map.svg",
        continent = Continent.AFRICA,
        subregion = Subregion.NORTHERN_AFRICA,
        population = 102334404,
        capital = "Cairo"
    ),
    Country(
        name = "Morocco",
        svgUrl = "https://upload.wikimedia.org/wikipedia/commons/2/2c/Flag_of_Morocco.svg",
        mapUrl = "https://upload.wikimedia.org/wikipedia/commons/e/eb/Morocco_location_map.svg",
        continent = Continent.AFRICA,
        subregion = Subregion.NORTHERN_AFRICA,
        population = 36910560,
        capital = "Rabat"
    ),
    Country(
        name = "South Africa",
        svgUrl = "https://upload.wikimedia.org/wikipedia/commons/a/af/Flag_of_South_Africa.svg",
        mapUrl = "https://upload.wikimedia.org/wikipedia/commons/2/26/South_Africa_location_map.svg",
        continent = Continent.AFRICA,
        subregion = Subregion.SOUTHERN_AFRICA,
        population = 59308690,
        capital = "Pretoria"
    ),
    Country(
        name = "Namibia",
        svgUrl = "https://upload.wikimedia.org/wikipedia/commons/0/00/Flag_of_Namibia.svg",
        mapUrl = "https://upload.wikimedia.org/wikipedia/commons/d/d4/Namibia_location_map.svg",
        continent = Continent.AFRICA,
        subregion = Subregion.SOUTHERN_AFRICA,
        population = 2540905,
        capital = "Windhoek"
    ),
    Country(
        name = "Kenya",
        svgUrl = "https://upload.wikimedia.org/wikipedia/commons/4/49/Flag_of_Kenya.svg",
        mapUrl = "https://upload.wikimedia.org/wikipedia/commons/a/a5/Kenya_location_map.svg",
        continent = Continent.AFRICA,
        subregion = Subregion.EASTERN_AFRICA,
        population = 53771296,
        capital = "Nairobi"
    ),
    Country(
        name = "Ethiopia",
        svgUrl = "https://upload.wikimedia.org/wikipedia/commons/7/71/Flag_of_Ethiopia.svg",
        mapUrl = "https://upload.wikimedia.org/wikipedia/commons/4/4e/Ethiopia_location_map.svg",
        continent = Continent.AFRICA,
        subregion = Subregion.EASTERN_AFRICA,
        population = 114963588,
        capital = "Addis Ababa"
    ),

    // ASIA
    Country(
        name = "China",
        svgUrl = "https://upload.wikimedia.org/wikipedia/commons/f/fa/Flag_of_the_People%27s_Republic_of_China.svg",
        mapUrl = "https://upload.wikimedia.org/wikipedia/commons/9/9e/China_location_map.svg",
        continent = Continent.ASIA,
        subregion = Subregion.EAST_ASIA,
        population = 1439323776,
        capital = "Beijing"
    ),
    Country(
        name = "Japan",
        svgUrl = "https://upload.wikimedia.org/wikipedia/en/9/9e/Flag_of_Japan.svg",
        mapUrl = "https://upload.wikimedia.org/wikipedia/commons/d/d4/Japan_location_map_with_side_map_of_the_Ryukyu_Islands.svg",
        continent = Continent.ASIA,
        subregion = Subregion.EAST_ASIA,
        population = 126476461,
        capital = "Tokyo"
    ),
    Country(
        name = "India",
        svgUrl = "https://upload.wikimedia.org/wikipedia/en/4/41/Flag_of_India.svg",
        mapUrl = "https://upload.wikimedia.org/wikipedia/commons/8/8c/India_location_map.svg",
        continent = Continent.ASIA,
        subregion = Subregion.SOUTH_ASIA,
        population = 1380004385,
        capital = "New Delhi"
    ),
    Country(
        name = "Pakistan",
        svgUrl = "https://upload.wikimedia.org/wikipedia/commons/3/32/Flag_of_Pakistan.svg",
        mapUrl = "https://upload.wikimedia.org/wikipedia/commons/a/a8/Pakistan_location_map.svg",
        continent = Continent.ASIA,
        subregion = Subregion.SOUTH_ASIA,
        population = 220892340,
        capital = "Islamabad"
    ),
    Country(
        name = "Saudi Arabia",
        svgUrl = "https://upload.wikimedia.org/wikipedia/commons/0/0d/Flag_of_Saudi_Arabia.svg",
        mapUrl = "https://upload.wikimedia.org/wikipedia/commons/4/47/Saudi_Arabia_location_map.svg",
        continent = Continent.ASIA,
        subregion = Subregion.WEST_ASIA,
        population = 34813871,
        capital = "Riyadh"
    ),
    Country(
        name = "Turkey",
        svgUrl = "https://upload.wikimedia.org/wikipedia/commons/b/b4/Flag_of_Turkey.svg",
        mapUrl = "https://upload.wikimedia.org/wikipedia/commons/c/c9/Turkey_location_map.svg",
        continent = Continent.ASIA,
        subregion = Subregion.WEST_ASIA,
        population = 84339067,
        capital = "Ankara"
    ),
    Country(
        name = "Indonesia",
        svgUrl = "https://upload.wikimedia.org/wikipedia/commons/9/9f/Flag_of_Indonesia.svg",
        mapUrl = "https://upload.wikimedia.org/wikipedia/commons/0/05/Indonesia_location_map.svg",
        continent = Continent.ASIA,
        subregion = Subregion.SOUTHEAST_ASIA,
        population = 273523615,
        capital = "Jakarta"
    ),
    Country(
        name = "Thailand",
        svgUrl = "https://upload.wikimedia.org/wikipedia/commons/a/a9/Flag_of_Thailand.svg",
        mapUrl = "https://upload.wikimedia.org/wikipedia/commons/4/46/Thailand_location_map.svg",
        continent = Continent.ASIA,
        subregion = Subregion.SOUTHEAST_ASIA,
        population = 69799978,
        capital = "Bangkok"
    ),

    // EUROPE
    Country(
        name = "Spain",
        svgUrl = "https://upload.wikimedia.org/wikipedia/en/9/9a/Flag_of_Spain.svg",
        mapUrl = "https://upload.wikimedia.org/wikipedia/commons/1/12/Spain_location_map.svg",
        continent = Continent.EUROPE,
        subregion = Subregion.SOUTHERN_EUROPE,
        population = 46754778,
        capital = "Madrid"
    ),
    Country(
        name = "Italy",
        svgUrl = "https://upload.wikimedia.org/wikipedia/en/0/03/Flag_of_Italy.svg",
        mapUrl = "https://upload.wikimedia.org/wikipedia/commons/9/97/Italy_location_map.svg",
        continent = Continent.EUROPE,
        subregion = Subregion.SOUTHERN_EUROPE,
        population = 60461826,
        capital = "Rome"
    ),
    Country(
        name = "France",
        svgUrl = "https://upload.wikimedia.org/wikipedia/en/c/c3/Flag_of_France.svg",
        mapUrl = "https://upload.wikimedia.org/wikipedia/commons/a/a4/France_location_map.svg",
        continent = Continent.EUROPE,
        subregion = Subregion.WESTERN_EUROPE,
        population = 65273511,
        capital = "Paris"
    ),
    Country(
        name = "Germany",
        svgUrl = "https://upload.wikimedia.org/wikipedia/en/b/ba/Flag_of_Germany.svg",
        mapUrl = "https://upload.wikimedia.org/wikipedia/commons/5/5a/Germany_location_map.svg",
        continent = Continent.EUROPE,
        subregion = Subregion.WESTERN_EUROPE,
        population = 83783942,
        capital = "Berlin"
    ),
    Country(
        name = "United Kingdom",
        svgUrl = "https://upload.wikimedia.org/wikipedia/en/a/ae/Flag_of_the_United_Kingdom.svg",
        mapUrl = "https://upload.wikimedia.org/wikipedia/commons/a/ae/United_Kingdom_location_map.svg",
        continent = Continent.EUROPE,
        subregion = Subregion.NORTHERN_EUROPE,
        population = 67886011,
        capital = "London"
    ),
    Country(
        name = "Sweden",
        svgUrl = "https://upload.wikimedia.org/wikipedia/en/4/4c/Flag_of_Sweden.svg",
        mapUrl = "https://upload.wikimedia.org/wikipedia/commons/3/3b/Sweden_location_map.svg",
        continent = Continent.EUROPE,
        subregion = Subregion.NORTHERN_EUROPE,
        population = 10099265,
        capital = "Stockholm"
    ),
    Country(
        name = "Russia",
        svgUrl = "https://upload.wikimedia.org/wikipedia/en/f/f3/Flag_of_Russia.svg",
        mapUrl = "https://upload.wikimedia.org/wikipedia/commons/3/3c/Russia_location_map.svg",
        continent = Continent.EUROPE,
        subregion = Subregion.EASTERN_EUROPE,
        population = 145934462,
        capital = "Moscow"
    ),
    Country(
        name = "Poland",
        svgUrl = "https://upload.wikimedia.org/wikipedia/en/1/12/Flag_of_Poland.svg",
        mapUrl = "https://upload.wikimedia.org/wikipedia/commons/0/0a/Poland_location_map.svg",
        continent = Continent.EUROPE,
        subregion = Subregion.EASTERN_EUROPE,
        population = 37846611,
        capital = "Warsaw"
    ),

    // AMERICA
    Country(
        name = "United States",
        svgUrl = "https://upload.wikimedia.org/wikipedia/en/a/a4/Flag_of_the_United_States.svg",
        mapUrl = "https://upload.wikimedia.org/wikipedia/commons/1/13/USA_location_map.svg",
        continent = Continent.AMERICA,
        subregion = Subregion.NORTH_AMERICA,
        population = 331002651,
        capital = "Washington, D.C."
    ),
    Country(
        name = "Canada",
        svgUrl = "https://upload.wikimedia.org/wikipedia/commons/c/cf/Flag_of_Canada.svg",
        mapUrl = "https://upload.wikimedia.org/wikipedia/commons/3/38/Canada_location_map.svg",
        continent = Continent.AMERICA,
        subregion = Subregion.NORTH_AMERICA,
        population = 37742154,
        capital = "Ottawa"
    ),
    Country(
        name = "Mexico",
        svgUrl = "https://upload.wikimedia.org/wikipedia/commons/f/fc/Flag_of_Mexico.svg",
        mapUrl = "https://upload.wikimedia.org/wikipedia/commons/c/c9/Mexico_location_map.svg",
        continent = Continent.AMERICA,
        subregion = Subregion.CENTRAL_AMERICA,
        population = 128932753,
        capital = "Mexico City"
    ),
    Country(
        name = "Guatemala",
        svgUrl = "https://upload.wikimedia.org/wikipedia/commons/e/ec/Flag_of_Guatemala.svg",
        mapUrl = "https://upload.wikimedia.org/wikipedia/commons/7/70/Guatemala_location_map.svg",
        continent = Continent.AMERICA,
        subregion = Subregion.CENTRAL_AMERICA,
        population = 17915568,
        capital = "Guatemala City"
    ),
    Country(
        name = "Cuba",
        svgUrl = "https://upload.wikimedia.org/wikipedia/commons/b/bd/Flag_of_Cuba.svg",
        mapUrl = "https://upload.wikimedia.org/wikipedia/commons/1/14/Cuba_location_map.svg",
        continent = Continent.AMERICA,
        subregion = Subregion.CARIBBEAN,
        population = 11326616,
        capital = "Havana"
    ),
    Country(
        name = "Jamaica",
        svgUrl = "https://upload.wikimedia.org/wikipedia/commons/0/0a/Flag_of_Jamaica.svg",
        mapUrl = "https://upload.wikimedia.org/wikipedia/commons/8/8c/Jamaica_location_map.svg",
        continent = Continent.AMERICA,
        subregion = Subregion.CARIBBEAN,
        population = 2961167,
        capital = "Kingston"
    ),
    Country(
        name = "Brazil",
        svgUrl = "https://upload.wikimedia.org/wikipedia/en/0/05/Flag_of_Brazil.svg",
        mapUrl = "https://upload.wikimedia.org/wikipedia/commons/b/bc/BRA_orthographic.svg",
        continent = Continent.AMERICA,
        subregion = Subregion.SOUTH_AMERICA,
        population = 212559417,
        capital = "Brasília"
    ),
    Country(
        name = "Argentina",
        svgUrl = "https://upload.wikimedia.org/wikipedia/commons/1/1a/Flag_of_Argentina.svg",
        mapUrl = "https://upload.wikimedia.org/wikipedia/commons/b/b2/Argentina_location_map.svg",
        continent = Continent.AMERICA,
        subregion = Subregion.SOUTH_AMERICA,
        population = 45195774,
        capital = "Buenos Aires"
    ),

    // OCEANIA
    Country(
        name = "Australia",
        svgUrl = "https://upload.wikimedia.org/wikipedia/en/b/b9/Flag_of_Australia.svg",
        mapUrl = "https://upload.wikimedia.org/wikipedia/commons/7/7d/Australia_location_map.svg",
        continent = Continent.OCEANIA,
        subregion = Subregion.AUSTRALIA_AND_NEW_ZEALAND,
        population = 25499884,
        capital = "Canberra"
    ),
    Country(
        name = "New Zealand",
        svgUrl = "https://upload.wikimedia.org/wikipedia/commons/3/3e/Flag_of_New_Zealand.svg",
        mapUrl = "https://upload.wikimedia.org/wikipedia/commons/4/42/New_Zealand_location_map.svg",
        continent = Continent.OCEANIA,
        subregion = Subregion.AUSTRALIA_AND_NEW_ZEALAND,
        population = 4822233,
        capital = "Wellington"
    ),
    Country(
        name = "Papua New Guinea",
        svgUrl = "https://upload.wikimedia.org/wikipedia/commons/e/e8/Flag_of_Papua_New_Guinea.svg",
        mapUrl = "https://upload.wikimedia.org/wikipedia/commons/9/97/Papua_New_Guinea_location_map.svg",
        continent = Continent.OCEANIA,
        subregion = Subregion.MELANESIA,
        population = 8947024,
        capital = "Port Moresby"
    ),
    Country(
        name = "Fiji",
        svgUrl = "https://upload.wikimedia.org/wikipedia/commons/b/ba/Flag_of_Fiji.svg",
        mapUrl = "https://upload.wikimedia.org/wikipedia/commons/5/55/Fiji_location_map.svg",
        continent = Continent.OCEANIA,
        subregion = Subregion.MELANESIA,
        population = 896445,
        capital = "Suva"
    ),
    Country(
        name = "Samoa",
        svgUrl = "https://upload.wikimedia.org/wikipedia/commons/3/31/Flag_of_Samoa.svg",
        mapUrl = "https://upload.wikimedia.org/wikipedia/commons/b/bc/Samoa_location_map.svg",
        continent = Continent.OCEANIA,
        subregion = Subregion.POLYNESIA,
        population = 198414,
        capital = "Apia"
    ),
    Country(
        name = "Tonga",
        svgUrl = "https://upload.wikimedia.org/wikipedia/commons/9/9a/Flag_of_Tonga.svg",
        mapUrl = "https://upload.wikimedia.org/wikipedia/commons/0/04/Tonga_location_map.svg",
        continent = Continent.OCEANIA,
        subregion = Subregion.POLYNESIA,
        population = 105695,
        capital = "Nuku'alofa"
    ),
    Country(
        name = "Micronesia",
        svgUrl = "https://upload.wikimedia.org/wikipedia/commons/e/e4/Flag_of_the_Federated_States_of_Micronesia.svg",
        mapUrl = "https://upload.wikimedia.org/wikipedia/commons/b/b2/Micronesia_location_map.svg",
        continent = Continent.OCEANIA,
        subregion = Subregion.MICRONESIA,
        population = 115023,
        capital = "Palikir"
    ),
    Country(
        name = "Palau",
        svgUrl = "https://upload.wikimedia.org/wikipedia/commons/4/48/Flag_of_Palau.svg",
        mapUrl = "https://upload.wikimedia.org/wikipedia/commons/9/97/Palau_location_map.svg",
        continent = Continent.OCEANIA,
        subregion = Subregion.MICRONESIA,
        population = 18094,
        capital = "Ngerulmud"
    )
)