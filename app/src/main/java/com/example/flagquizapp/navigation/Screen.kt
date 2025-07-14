package com.example.flagquizapp.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object ContinentSelection : Screen("continent_selection")
    // note the `{continent}` arg in the route:
    object FlagQuiz : Screen("flag_quiz/{continent}") {
        // helper to build the actual path
        fun createRoute(continent: String) = "flag_quiz/$continent"
    }
}
