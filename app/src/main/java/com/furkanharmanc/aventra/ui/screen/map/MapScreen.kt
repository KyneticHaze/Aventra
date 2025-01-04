package com.furkanharmanc.aventra.ui.screen.map

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapScreen(modifier: Modifier = Modifier,navController: NavController) {
    Scaffold(
        modifier = modifier,
        topBar = {
         TopAppBar(
             title = { Text(text = "Aventra Map", style = MaterialTheme.typography.headlineMedium) },
             navigationIcon = { IconButton(onClick = navController::navigateUp) {
             Icon(
                 imageVector = Icons.AutoMirrored.Default.ArrowBack,
                 contentDescription = "Get Back"
             )
         }},
             )
        }
    ) { innerPadding ->

        var markerList by remember { mutableStateOf(listOf<MarkerInfo>()) }
        var selectedMarker by remember { mutableStateOf<MarkerInfo?>(null) }

        DynamicMapWithTooltip(
            modifier = Modifier.padding(innerPadding),
            markers = markerList,
            onMapClick = { position ->
                val newMarker = MarkerInfo(
                    position = position,
                    title = "İşaret",
                    snippet = "Enlem: ${position.latitude}\nBoylam: ${position.longitude}"
                )
                markerList += newMarker
            },
            onMarkerClick = { markerPosition -> selectedMarker = markerPosition },
            toolTipContent = selectedMarker
        )
    }
}