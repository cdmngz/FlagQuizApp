package com.example.flagquizapp.navigation

sealed class Screen(val route: String) {
    data object Home : Screen("home")
    data object DailyGame : Screen("daily_game")
    data object ContinentSelection : Screen("continent_selection")
    data object SubRegionSelection : Screen("subregion_selection/{continent}") {
        fun createRoute(continent: String) = "subregion_selection/$continent"
    }
    data object FlagQuiz : Screen("flag_quiz/{subregion}/{quizType}") {
        fun createRoute(subregion: String, quizType: Int) = "flag_quiz/$subregion/$quizType"
    }
    data object Settings : Screen("settings")
    data object ThankYou : Screen("thank_you")
}
