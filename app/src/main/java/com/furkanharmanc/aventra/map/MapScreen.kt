package com.furkanharmanc.aventra.map

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

@Composable
fun MapScreen(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {

        var markerList by remember { mutableStateOf(listOf<MarkerInfo>()) }
        var selectedMarker by remember { mutableStateOf<MarkerInfo?>(null) }

        DynamicMapWithTooltip(
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