"""
Generate Kotlin data + download SVG flags.
Usage:
    python3 -m venv env
    source env/bin/activate
    pip install requests
    python3 tools/generate_countries.py
"""

import os
import json
import requests
from datetime import datetime, timezone

# ---------- CONFIG ----------
FLAGS_DIR = "app/src/main/assets/flags"  # Where the SVGs will go
KOTLIN_FILE = "app/src/main/java/com/example/flagquizapp/data/GeneratedCountries.kt"
COUNTRIES_JSON = "public/countries.json"
RESTCOUNTRIES_URL = (
    "https://restcountries.com/v3.1/all"
    "?fields=cca2,flags,name,capital,population,region,subregion"
)
# ----------------------------

# Emojis
SUCCESS = "✅"
FAIL = "❌"
SKIP = "⏭️"

# Global counters
ok_count = 0
fail_count = 0
skip_count = 0

# Normalization: maps API variants to canonical keys
normalization_map = {
    "Eastern Asia": "East Asia",
    "North America": "Northern America",
    "South-Eastern Asia": "Southeast Asia",
}

# Maps to Kotlin enum values
continent_map = {
    "Africa": "AFRICA",
    "Americas": "AMERICA",
    "Asia": "ASIA",
    "Europe": "EUROPE",
    "Oceania": "OCEANIA",
    "Antarctic": "ANTARCTICA",
}

subregion_map = {
    # Africa
    "Northern Africa": "NORTHERN_AFRICA",
    "Sub-Saharan Africa": "SUB_SAHARAN_AFRICA",
    "Eastern Africa": "EASTERN_AFRICA",
    "Western Africa": "WESTERN_AFRICA",
    "Middle Africa": "CENTRAL_AFRICA",
    "Southern Africa": "SOUTHERN_AFRICA",
    # Americas
    "Northern America": "NORTHERN_AMERICA",
    "Central America": "CENTRAL_AMERICA",
    "Caribbean": "CARIBBEAN",
    "South America": "SOUTH_AMERICA",
    # Asia
    "Central Asia": "CENTRAL_ASIA",
    "East Asia": "EAST_ASIA",
    "South Asia": "SOUTH_ASIA",
    "Southern Asia": "SOUTH_ASIA",
    "Southeast Asia": "SOUTHEAST_ASIA",
    "Western Asia": "WEST_ASIA",
    # Europe
    "Northern Europe": "NORTHERN_EUROPE",
    "Southern Europe": "SOUTHERN_EUROPE",
    "Eastern Europe": "EASTERN_EUROPE",
    "Western Europe": "WESTERN_EUROPE",
    "Southeast Europe": "SOUTH_EAST_EUROPE",
    "Central Europe": "CENTRAL_EUROPE",
    # Oceania
    "Australia and New Zealand": "AUSTRALIA_AND_NEW_ZEALAND",
    "Melanesia": "MELANESIA",
    "Micronesia": "MICRONESIA",
    "Polynesia": "POLYNESIA",
    # Antarctica
    "Antarctic": "ANTARCTIC_REGION",
    # Fallback
    "": "ANTARCTIC_REGION",
}


def safe_str(s: str) -> str:
    return s.replace('"', '\\"') if s else ""


def normalize(value: str) -> str:
    return normalization_map.get(value, value)


def download_svg(svg_url: str, code: str, name: str) -> str:
    """
    Download the SVG for the given code.
    Returns the filename (or "" on failure).
    """
    global ok_count, fail_count, skip_count

    os.makedirs(FLAGS_DIR, exist_ok=True)
    filename = f"flag_{code.lower()}.svg"
    path = os.path.join(FLAGS_DIR, filename)

    if os.path.exists(path):
        print(f"{SKIP} {code} – {name} (already exists)")
        skip_count += 1
        return filename

    try:
        r = requests.get(svg_url, timeout=30, headers={"User-Agent": "Mozilla/5.0"})
        r.raise_for_status()
        with open(path, "wb") as f:
            f.write(r.content)
        print(f"{SUCCESS} {code} – {name} saved as {filename}")
        ok_count += 1
        return filename
    except Exception as e:
        print(f"{FAIL} {code} – {name}: {e}")
        fail_count += 1
        return ""


def generate_kotlin_entry(country: dict) -> str:
    name = safe_str(country.get("name", {}).get("common", "Unknown"))
    code = country.get("cca2", "XX").upper()
    capital = safe_str(country.get("capital", [""])[0]) if country.get("capital") else ""
    population = country.get("population", 0)
    svg_url_remote = safe_str(country.get("flags", {}).get("svg", ""))
    region = normalize(country.get("region", ""))
    subregion = normalize(country.get("subregion", ""))

    # Download SVG & create local URI
    if svg_url_remote:
        filename = download_svg(svg_url_remote, code, name)
        local_uri = f"file:///android_asset/flags/{filename}" if filename else ""
    else:
        print(f"{FAIL} {code} – {name}: no SVG URL")
        local_uri = ""

    continent_enum = f"Continent.{continent_map.get(region, 'TODO')}"
    subregion_enum = f"Subregion.{subregion_map.get(subregion, 'TODO')}"

    return f"""    Country(
        name = "{name}",
        code = "{code}",
        svgUrl = "{local_uri}",
        mapUrl = "TODO",
        continent = {continent_enum},
        subregion = {subregion_enum},
        population = {population},
        capital = "{capital}"
    ),"""


def main():
    # Fetch data
    response = requests.get(RESTCOUNTRIES_URL, headers={"User-Agent": "Mozilla/5.0"})
    if response.status_code != 200:
        print(f"{FAIL} Error fetching countries: {response.status_code}")
        return

    countries = sorted(response.json(), key=lambda c: c.get("name", {}).get("common", ""))

    # Generate Kotlin file
    os.makedirs(os.path.dirname(KOTLIN_FILE), exist_ok=True)
    with open(KOTLIN_FILE, "w", encoding="utf-8") as f:
        timestamp = datetime.now(timezone.utc).isoformat(timespec="seconds").replace("+00:00", "Z")
        f.write(f"// AUTO-GENERATED FILE – DO NOT EDIT (Generated at {timestamp} UTC)\n")
        f.write("package com.example.flagquizapp.data\n\n")
        f.write("import com.example.flagquizapp.model.Country\n")
        f.write("import com.example.flagquizapp.model.Continent\n")
        f.write("import com.example.flagquizapp.model.Subregion\n\n")
        f.write("fun getWorldCountries(): List<Country> = listOf(\n")

        # Build entries + download
        for entry in countries:
            f.write(generate_kotlin_entry(entry) + "\n")

        f.write(")\n")

    print(f"\n✅ Kotlin file generated: {KOTLIN_FILE}")

    # Check unmapped enums
    all_regions = {normalize(c.get("region", "")) for c in countries}
    all_subregions = {normalize(c.get("subregion", "")) for c in countries}
    missing_regions = sorted(all_regions - set(continent_map))
    missing_subregions = sorted(all_subregions - set(subregion_map))

    if missing_regions:
        print("⚠️ Missing regions:", missing_regions)
    if missing_subregions:
        print("⚠️ Missing subregions:", missing_subregions)

    # Optional JSON for GitHub Pages (uses relative path to flags dir)
    json_output = [
        {
            "name": c.get("name", {}).get("common", ""),
            "code": c.get("cca2", "XX").upper(),
            "flag": f"flags/flag_{c.get('cca2', 'XX').lower()}.svg",
            "population": c.get("population", 0),
            "capital": c.get("capital", [''])[0] if c.get("capital") else "",
            "region": normalize(c.get("region", "")),
            "subregion": normalize(c.get("subregion", "")),
        }
        for c in countries
    ]

    os.makedirs(os.path.dirname(COUNTRIES_JSON), exist_ok=True)
    with open(COUNTRIES_JSON, "w", encoding="utf-8") as fjson:
        json.dump(json_output, fjson, indent=2, ensure_ascii=False)

    print(f"🌍 JSON saved to: {COUNTRIES_JSON}")
    print(f"🏳️ SVGs saved to: {FLAGS_DIR}")

    # Summary
    print(f"\nSummary: {SUCCESS} {ok_count}  {SKIP} {skip_count}  {FAIL} {fail_count}")


if __name__ == "__main__":
    main()
