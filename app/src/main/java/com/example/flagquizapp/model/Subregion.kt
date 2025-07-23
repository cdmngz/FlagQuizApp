package com.example.flagquizapp.model

enum class Subregion(val displayName: String, val continent: Continent) {
    // Africa
    NORTHERN_AFRICA("Northern Africa", Continent.AFRICA),
    SUB_SAHARAN_AFRICA("Sub-Saharan Africa", Continent.AFRICA),
    EASTERN_AFRICA("Eastern Africa", Continent.AFRICA),
    WESTERN_AFRICA("Western Africa", Continent.AFRICA),
    CENTRAL_AFRICA("Middle Africa", Continent.AFRICA), // use "Middle Africa" from API
    SOUTHERN_AFRICA("Southern Africa", Continent.AFRICA),

    // America
    NORTHERN_AMERICA("Northern America", Continent.AMERICA), // API uses "Northern America"
    CENTRAL_AMERICA("Central America", Continent.AMERICA),
    CARIBBEAN("Caribbean", Continent.AMERICA),
    SOUTH_AMERICA("South America", Continent.AMERICA),

    // Asia
    CENTRAL_ASIA("Central Asia", Continent.ASIA),
    EAST_ASIA("East Asia", Continent.ASIA),
    SOUTH_ASIA("South Asia", Continent.ASIA),
    SOUTHEAST_ASIA("Southeast Asia", Continent.ASIA),
    WEST_ASIA("Western Asia", Continent.ASIA),

    // Europe
    NORTHERN_EUROPE("Northern Europe", Continent.EUROPE),
    SOUTHERN_EUROPE("Southern Europe", Continent.EUROPE),
    EASTERN_EUROPE("Eastern Europe", Continent.EUROPE),
    WESTERN_EUROPE("Western Europe", Continent.EUROPE),
    SOUTH_EAST_EUROPE("Southeast Europe", Continent.EUROPE),
    CENTRAL_EUROPE("Central Europe", Continent.EUROPE),

    // Oceania
    AUSTRALIA_AND_NEW_ZEALAND("Australia and New Zealand", Continent.OCEANIA),
    MELANESIA("Melanesia", Continent.OCEANIA),
    MICRONESIA("Micronesia", Continent.OCEANIA),
    POLYNESIA("Polynesia", Continent.OCEANIA),

    // Antarctica
    ANTARCTIC_REGION("Antarctica", Continent.ANTARCTICA)
}