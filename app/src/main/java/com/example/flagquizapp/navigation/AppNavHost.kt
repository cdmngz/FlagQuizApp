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
import com.example.flagquizapp.model.Continent
import com.example.flagquizapp.model.QuizType
import com.example.flagquizapp.model.Subregion
import com.example.flagquizapp.ui.screens.ContinentSelectionScreen
import com.example.flagquizapp.ui.screens.DailyGameScreen
import com.example.flagquizapp.ui.screens.HomeScreen
import com.example.flagquizapp.ui.screens.SettingsScreen
import com.example.flagquizapp.ui.screens.SubRegionScreen
import com.example.flagquizapp.ui.screens.ThankYouScreen
import com.example.flagquizapp.ui.screens.quiz.FlagQuizScreen
import com.example.flagquizapp.ui.screens.quiz.MapQuizScreen
import com.example.flagquizapp.ui.screens.quiz.MixedQuizScreen
import com.example.flagquizapp.ui.screens.quiz.TimedQuizScreen
import com.example.flagquizapp.ui.theme.ThemeOption

@Composable
fun AppNavHost(
    navController: NavHostController,
    currentTheme: ThemeOption,
    onThemeChange: (ThemeOption) -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        composable(Screen.Home.route) {
            HomeScreen(
                onPlayDailyGame = { navController.navigate(Screen.DailyGame.route) },
                onPlayWorldQuiz = { navController.navigate(Screen.ContinentSelection.route) },
                onPlayCapitalQuiz = { navController.navigate(Screen.ContinentSelection.route) },
                onPlayFootballQuiz = { navController.navigate(Screen.ContinentSelection.route) },
                onOpenSettings = { navController.navigate(Screen.Settings.route) }
            )
        }

        composable(Screen.DailyGame.route) {
            val country = remember { getWorldCountries().random() }
            DailyGameScreen(
                country = country,
                onGoBack = { navController.navigateUp() },
                onFinish = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Home.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.Settings.route) {
            SettingsScreen(
                onGoBack = { navController.navigateUp() },
                onThankYou = { navController.navigate(Screen.ThankYou.route) },
                currentTheme = currentTheme,
                onThemeChange = onThemeChange
            )
        }

        composable(Screen.ThankYou.route) {
            ThankYouScreen(onGoBack = { navController.navigateUp() })
        }

        composable(Screen.ContinentSelection.route) {
            ContinentSelectionScreen(
                onSelectContinent = { continent ->
                    navController.navigate(Screen.SubRegionSelection.createRoute(continent.name))
                },
                onGoBack = { navController.navigateUp() }
            )
        }

        composable(
            route = Screen.SubRegionSelection.route,
            arguments = listOf(navArgument("continent") { type = NavType.StringType })
        ) { backStackEntry ->
            val continentName = backStackEntry.arguments?.getString("continent") ?: return@composable
            val continent = Continent.valueOf(continentName)

            SubRegionScreen(
                continent = continent,
                onGoBack = { navController.navigateUp() },
                onSelectSubregion = { subregion, buttonIndex ->
                    navController.navigate(Screen.FlagQuiz.createRoute(subregion.name, buttonIndex))
                },
                onSelectAll = { buttonIndex ->
                    navController.navigate(Screen.FlagQuizAll.createRoute(continent.name, buttonIndex))
                }
            )
        }

        composable(
            route = Screen.FlagQuizAll.route,
            arguments = listOf(
                navArgument("continent") { type = NavType.StringType },
                navArgument("quizType") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val continentName = backStackEntry.arguments?.getString("continent") ?: return@composable
            val quizTypeIndex = backStackEntry.arguments?.getInt("quizType") ?: 0

            val continent = Continent.valueOf(continentName)
            val countries = remember(continent) {
                getWorldCountries().filter { it.continent == continent }
            }

            when (QuizType.from(quizTypeIndex)) {
                QuizType.FLAGS -> FlagQuizScreen(
                    countries = countries,
                    navController = navController,
                    title = continent.displayName
                )
                QuizType.MAPS  -> MapQuizScreen(countries, navController)
                QuizType.MIXED -> MixedQuizScreen(countries, navController)
                QuizType.TIMED -> TimedQuizScreen(countries, navController)
            }
        }

        composable(
            route = Screen.FlagQuiz.route,
            arguments = listOf(
                navArgument("subregion") { type = NavType.StringType },
                navArgument("quizType") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val subregionName = backStackEntry.arguments?.getString("subregion") ?: return@composable
            val quizTypeIndex = backStackEntry.arguments?.getInt("quizType") ?: 0

            val subregion = Subregion.valueOf(subregionName)
            val countries = remember(subregion) {
                getWorldCountries().filter { it.subregion == subregion }
            }

            when (QuizType.from(quizTypeIndex)) {
                QuizType.FLAGS -> FlagQuizScreen(
                    countries = countries,
                    navController = navController,
                    title = subregion.displayName
                )
                QuizType.MAPS  -> MapQuizScreen(countries, navController)
                QuizType.MIXED -> MixedQuizScreen(countries, navController)
                QuizType.TIMED -> TimedQuizScreen(countries, navController)
            }
        }
    }
}
