package com.furkanharmanc.aventra.ui.screen.main

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
import com.furkanharmanc.aventra.ui.screen.account.AccountScreen
import com.furkanharmanc.aventra.ui.screen.chat.ChatScreen
import com.furkanharmanc.aventra.ui.screen.map.MapScreen
import com.furkanharmanc.aventra.ui.screen.settings.SettingsScreen
import com.furkanharmanc.aventra.ui.screen.travelbook.TravelBookScreen
import com.furkanharmanc.aventra.ui.theme.AventraTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
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
                        startDestination = Screen.Main.name,
                    ) {
                        composable(route = Screen.Main.name) {
                            MainScreen(navController = navController)
                        }
                        composable(route = Screen.Map.name) {
                            MapScreen(navController = navController)
                        }
                        composable(Screen.Chat.name) {
                            ChatScreen(navController = navController)
                        }
                        composable(Screen.TravelBook.name) {
                            TravelBookScreen(navController = navController)
                        }
                        composable(Screen.Settings.name) {
                            SettingsScreen()
                        }
                        composable(Screen.Account.name) {
                            AccountScreen()
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
    Settings,
    Account,
}