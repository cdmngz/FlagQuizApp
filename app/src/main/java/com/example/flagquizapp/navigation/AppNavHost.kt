package com.example.flagquizapp.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.flagquizapp.data.getWorldCountries
import com.example.flagquizapp.model.Continents
import com.example.flagquizapp.ui.screens.ContinentSelectionScreen
import com.example.flagquizapp.ui.screens.DailyGameScreen
import com.example.flagquizapp.ui.screens.FlagQuizScreen
import com.example.flagquizapp.ui.screens.HomeScreen
import com.example.flagquizapp.ui.screens.ThankYouScreen

@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(
        navController  = navController,
        startDestination = Screen.Home.route,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // 1) Home → Continent picker
        composable(Screen.Home.route) {
            HomeScreen(
                onPlayDailyGame = {
                    navController.navigate(Screen.DailyGame.route)
                },
                onPlayWorldQuiz = {
                    navController.navigate(Screen.ContinentSelection.route)
                },
                onPlayCapitalQuiz = {
                    navController.navigate(Screen.ContinentSelection.route)
                },
                onPlayFootballQuiz = {
                    navController.navigate(Screen.ContinentSelection.route)
                },
                onThankYou = {
                    navController.navigate(Screen.ThankYou.route)
                }
            )
        }

        composable(Screen.DailyGame.route) {
            val country = remember { getWorldCountries().random() }

            DailyGameScreen(
                country = country,
                onFinish = {
                    navController.popBackStack()
                }
            )
        }

        composable(Screen.ThankYou.route) {
            ThankYouScreen()
        }


        composable(Screen.ContinentSelection.route) {
            ContinentSelectionScreen(
                onSelectContinent = { continent ->
                    navController.navigate(
                        Screen.FlagQuiz.createRoute(continent.name)
                    )
                },
                onGoBack = { navController.popBackStack() }
            )
        }

        composable(
            route = Screen.FlagQuiz.route,
            arguments = listOf(
                navArgument("continent") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val continentName = backStackEntry
                .arguments
                ?.getString("continent")
                ?: error("Missing continent")

            val continent = Continents.valueOf(continentName)
            val countries = getWorldCountries()
                .filter { it.continent == continent }

            FlagQuizScreen(
                countries    = countries,
                navController = navController
            )
        }
    }
}
