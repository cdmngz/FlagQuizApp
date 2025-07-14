package com.example.flagquizapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument                    // navArgument is now here, not in compose :contentReference[oaicite:0]{index=0}
import com.example.flagquizapp.data.getWorldCountries
import com.example.flagquizapp.model.Continents
import com.example.flagquizapp.ui.screens.ContinentSelectionScreen
import com.example.flagquizapp.ui.screens.FlagQuizScreen
import com.example.flagquizapp.ui.screens.HomeScreen

@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(
        navController  = navController,
        startDestination = Screen.Home.route
    ) {
        // 1) Home → Continent picker
        composable(Screen.Home.route) {
            HomeScreen(
                onPlayWorldQuiz = {
                    navController.navigate(Screen.ContinentSelection.route)
                },
                onPlayCapitalQuiz = {
                    navController.navigate(Screen.ContinentSelection.route)
                },
                onPlayFootballQuiz = {
                    navController.navigate(Screen.ContinentSelection.route)
                }
            )
        }

        // 2) Choose your continent
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

        // 3) Quiz screen, now with a “continent” argument
        composable(
            route = Screen.FlagQuiz.route,
            arguments = listOf(
                navArgument("continent") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->                            // explicit parameter lets Kotlin infer T :contentReference[oaicite:1]{index=1}
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
