package com.furkanharmanc.aventra.ui.screen.map

import com.google.android.gms.maps.model.LatLng

data class MarkerInfo(
    val position: LatLng,
    val title: String,
    val snippet: String
)