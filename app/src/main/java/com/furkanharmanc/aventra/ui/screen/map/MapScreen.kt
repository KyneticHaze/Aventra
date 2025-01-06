package com.furkanharmanc.aventra.ui.screen.map

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.furkanharmanc.aventra.data.TravelBook
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapEffect
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.MapsComposeExperimentalApi
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapScreen(
    modifier: Modifier = Modifier,
    viewModel: MapViewModel = hiltViewModel(),
    navController: NavController
) {

    val viewState by viewModel.viewState.collectAsState()
    val scope = rememberCoroutineScope()

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(41.0082, 28.9784), 15f)
    }

    val mapProperties by remember { mutableStateOf( MapProperties(
        mapType = MapType.TERRAIN,
        isMyLocationEnabled = true
    )) }

    val uiSettings by remember {
        mutableStateOf(MapUiSettings(
            zoomControlsEnabled = true,
            myLocationButtonEnabled = true
        ))
    }

    Scaffold(
        modifier = modifier,
        topBar = {
         TopAppBar(
             title = { Text(text = "Aventra Map", style = MaterialTheme.typography.headlineMedium) },
             navigationIcon = {
                 IconButton(onClick = navController::navigateUp) {
                     Icon(
                         imageVector = Icons.AutoMirrored.Default.ArrowBack,
                         contentDescription = "Get Back"
                     )
                 }
                              },
             )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPositionState,
                properties = mapProperties,
                uiSettings = uiSettings,
                onMapClick = { latLng ->
                    viewModel.resetTravelBook()
                    val newTravelBook = TravelBook(
                        title = "Yeni Konum",
                        snippet = "Konum Açıklaması",
                        latitude = latLng.latitude,
                        longitude = latLng.longitude
                    )
                    scope.launch {
                        viewModel.upsertTravelBook(newTravelBook)
                    }
                }
            ) {
                viewState.travelBooks.forEach { travelBook ->
                    MarkerWithToolTip(
                        travelBook = travelBook,
                        isSelected = viewState.selectedTravel == travelBook,
                        onMarkerClick = {
                            viewModel.selectTravelBook(travelBook)
                            scope.launch {
                                cameraPositionState.animate(
                                    update = CameraUpdateFactory.newLatLng(
                                        LatLng(travelBook.latitude, travelBook.longitude)
                                    ),
                                    durationMs = 500
                                )
                            }
                        },
                        onEdit = { updatedTravelBook ->
                            scope.launch {
                                viewModel.upsertTravelBook(updatedTravelBook)
                                viewModel.resetTravelBook()
                            }
                        },
                        onDelete = {
                            scope.launch {
                                viewModel.deleteTravelBook(travelBook)
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun MarkerWithToolTip(
    travelBook: TravelBook,
    isSelected: Boolean,
    onMarkerClick: () -> Unit,
    onEdit: (TravelBook) -> Unit,
    onDelete: () -> Unit
) {
    val markerState = rememberMarkerState(
        position = LatLng(travelBook.latitude, travelBook.longitude)
    )

    Marker(
        state = markerState,
        title = travelBook.title,
        snippet = travelBook.snippet,
        onClick = {
            onMarkerClick()
            true
        }
    )

    if (isSelected) {
        ToolTip(
            travelBook = travelBook,
            markerState = markerState,
            onEdit = onEdit,
            onDelete = onDelete
        )
    }
}

@OptIn(MapsComposeExperimentalApi::class)
@Composable
fun ToolTip(travelBook: TravelBook, markerState: MarkerState, onEdit: (TravelBook) -> Unit, onDelete: () -> Unit) {

    var showDeleteDialog by remember { mutableStateOf(false) }
    var showEditDialog by remember { mutableStateOf(false) }
    var editedTitle by remember { mutableStateOf(travelBook.title) }
    var editedSnippet by remember { mutableStateOf(travelBook.snippet) }
    var tooltipOffset by remember { mutableStateOf(IntOffset.Zero) }

    MapEffect(markerState.position) { map ->
        val projection = map.projection
        val markerPoint = projection.toScreenLocation(markerState.position)
        tooltipOffset = IntOffset(
            x = markerPoint.x - 150,
            y = markerPoint.y - 200
        )
    }

    if (showEditDialog) {
        AlertDialog(
            onDismissRequest = { showEditDialog = false },
            title = { Text("Konumu Düzenle") },
            text = {
                Column {
                    TextField(
                        value = editedTitle,
                        onValueChange = { editedTitle = it },
                        label = { Text("Başlık") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    TextField(
                        value = editedSnippet,
                        onValueChange = { editedSnippet = it },
                        label = { Text("Açıklama") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        onEdit(travelBook.copy(
                            title = editedTitle,
                            snippet = editedSnippet
                        ))
                        showEditDialog = false
                    }
                ) {
                    Text("Kaydet")
                }
            },
            dismissButton = {
                TextButton(onClick = { showEditDialog = false }) {
                    Text("İptal")
                }
            }
        )
    }


    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text(text = "Konumu Sil") },
            text = { Text(text = "Bu konumu silmek istediğinize emin misiniz") },
            confirmButton = {
                TextButton(onClick = {
                    onDelete()
                    showDeleteDialog = false
                }) {
                    Text(text = "Sil")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) {
                    Text(text = "İptal")
                }
            }
        )
    }



    Surface(
        modifier = Modifier
            .offset{ tooltipOffset }
            .width(300.dp),
        shape = MaterialTheme.shapes.medium,
        tonalElevation = 8.dp,
        shadowElevation = 8.dp,
        color = MaterialTheme.colorScheme.surface
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = travelBook.title,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = travelBook.snippet,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(
                    onClick = {
                        showEditDialog = true
                    }
                ) {
                    Text("Düzenle")
                }
                TextButton(
                    onClick = {
                        showDeleteDialog = true
                    }
                ) {
                    Text("Sil")
                }
            }
    }
    }
}