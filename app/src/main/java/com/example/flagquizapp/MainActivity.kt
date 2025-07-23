package com.example.flagquizapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.rememberNavController
import com.example.flagquizapp.data.ThemeStore
import com.example.flagquizapp.navigation.AppNavHost
import com.example.flagquizapp.ui.theme.FlagQuizAppTheme
import com.example.flagquizapp.ui.theme.ThemeOption
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val context = LocalContext.current
            val scope = rememberCoroutineScope()

            val currentTheme by ThemeStore.themeFlow(context).collectAsState(initial = ThemeOption.SYSTEM)

            FlagQuizAppTheme(themeOption = currentTheme) {
                Surface(modifier = Modifier.fillMaxSize()) {
                    val navController = rememberNavController()
                    AppNavHost(
                        navController = navController,
                        currentTheme = currentTheme,
                        onThemeChange = { option ->
                            scope.launch { ThemeStore.setTheme(context, option) }
                        }
                    )
                }
            }
        }
    }
}
