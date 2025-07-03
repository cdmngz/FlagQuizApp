package com.example.flagquizapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.flagquizapp.data.getWorldCountries
import com.example.flagquizapp.ui.screens.FlagQuizScreen
import com.example.flagquizapp.ui.screens.HomeScreen

@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) {
            // ✅ This is inside a Composable block!
            HomeScreen(
                onPlayWorldQuiz = { navController.navigate(Screen.FlagQuiz.route) }
            )
        }
        composable(Screen.FlagQuiz.route) {
            FlagQuizScreen(
                countries = getWorldCountries(),
                navController = navController
            )
        }

    }
}
