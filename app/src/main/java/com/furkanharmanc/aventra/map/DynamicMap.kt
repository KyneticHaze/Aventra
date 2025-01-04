package com.furkanharmanc.aventra.map

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState

@Composable
fun DynamicMapWithTooltip(
    modifier: Modifier = Modifier,
    initialLocation: LatLng = LatLng(41.0082, 28.9784), // Istanbul
    zoomLevel: Float = 15f, // Yakınlaşma Seviyesi
    markers: List<MarkerInfo>,
    onMapClick: (LatLng) -> Unit = {}, // Haritaya Tıklama İşlevi
    onMarkerClick: (MarkerInfo) -> Unit = {}, // Marker'a Tıklama İşlevi,
    toolTipContent: MarkerInfo? = null
) {
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(initialLocation, zoomLevel)
    }
    val properties by remember { mutableStateOf(MapProperties(mapType = MapType.TERRAIN)) }

    GoogleMap(
        modifier = modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState,
        properties = properties,
        onMapClick = onMapClick
    ) {
        markers.forEach { markersInfo ->
            Marker(
                state = rememberMarkerState(position = markersInfo.position),
                title = markersInfo.title,
                snippet = markersInfo.snippet,
                onClick = {
                    onMarkerClick(markersInfo)
                    true // Marker tıklaması ok!
                }
            )
        }
    }
    toolTipContent?.let {
        ToolTipPopUp(markerInfo = it)
    }
}