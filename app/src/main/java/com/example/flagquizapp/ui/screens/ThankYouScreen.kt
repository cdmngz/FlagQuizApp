package com.example.flagquizapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ThankYouScreen() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Thanks for the support ❤️",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(32.dp))

            SelectionContainer {
                Column(horizontalAlignment = Alignment.Start) {
                    Column {
                        Text(text = "🌟 BTC", fontWeight = FontWeight.SemiBold)
                        Text(text = "btc", fontSize = 14.sp)
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Column {
                        Text(text = "💎 ETH", fontWeight = FontWeight.SemiBold)
                        Text(text = "eth", fontSize = 14.sp)
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Column {
                        Text(text = "🐶 DOGE", fontWeight = FontWeight.SemiBold)
                        Text(text = "dg", fontSize = 14.sp)
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Column {
                        Text(text = "🌊 SOL", fontWeight = FontWeight.SemiBold)
                        Text(text = "sol", fontSize = 14.sp)
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "Every bit helps. You're awesome!",
                fontSize = 16.sp
            )
        }
    }
}
