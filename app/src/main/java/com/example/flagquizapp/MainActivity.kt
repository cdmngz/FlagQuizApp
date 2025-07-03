package com.example.flagquizapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Surface
import androidx.navigation.compose.rememberNavController
import com.example.flagquizapp.navigation.AppNavHost
import com.example.flagquizapp.ui.theme.FlagQuizAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            FlagQuizAppTheme {
                Surface {
                    val navController = rememberNavController()
                    AppNavHost(navController = navController) // ✅ Use your navigation
                }
            }
        }
    }
}
