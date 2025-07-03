package com.example.flagquizapp.navigation

sealed class Screen(val route: String) {
    object FlagQuiz : Screen("flag_quiz")
    object Home     : Screen("home")
}
