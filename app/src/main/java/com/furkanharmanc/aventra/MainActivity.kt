package com.furkanharmanc.aventra

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.furkanharmanc.aventra.chat.ChatScreen
import com.furkanharmanc.aventra.map.MapScreen
import com.furkanharmanc.aventra.ui.theme.AventraTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AventraTheme {
                val navController = rememberNavController()
                Scaffold { innerPadding ->
                    NavHost(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                            .background(MaterialTheme.colorScheme.background),
                        navController = navController,
                        startDestination = Screen.Chat.name,
                    ) {
                        composable(route = Screen.Main.name) {
                            MainScreen()
                        }
                        composable(route = Screen.Map.name) {
                            MapScreen()
                        }
                        composable(Screen.Chat.name) {
                            ChatScreen()
                        }
                    }
                }
            }
        }
    }
}

enum class Screen {
    Main,
    Map,
    Chat,
    TravelBook,
    Account,
}