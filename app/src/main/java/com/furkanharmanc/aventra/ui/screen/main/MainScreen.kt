package com.furkanharmanc.aventra.ui.screen.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.furkanharmanc.aventra.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Aventra", style = MaterialTheme.typography.headlineLarge)
                },
                actions = {
                    IconButton(onClick = { navController.navigate(Screen.Map.name) }) {
                        Icon(imageVector = Icons.Outlined.LocationOn, contentDescription = "Map")
                    }
                    IconButton(onClick = { navController.navigate(Screen.Chat.name) }) {
                        Icon(painter = painterResource(id = R.drawable.outline_chat_24), contentDescription = "Ai with Chat")
                    }
                    IconButton(onClick = { navController.navigate(Screen.TravelBook.name) }) {
                        Icon(imageVector = Icons.Outlined.Favorite, contentDescription = "Favorite Place")
                    }
                }
    )
},
        ) { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            ElevatedCard(
                onClick = {
                    navController.navigate(Screen.Map.name)
                }
            ) {

            }
        }
    }
}