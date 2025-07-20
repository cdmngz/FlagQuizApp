package com.example.flagquizapp.navigation

sealed class Screen(val route: String) {
    data object Home : Screen("home")
    data object DailyGame : Screen("daily_game")
    data object ContinentSelection : Screen("continent_selection")
    data object FlagQuiz : Screen("flag_quiz/{continent}") {
        fun createRoute(continent: String) = "flag_quiz/$continent"
    }
}
