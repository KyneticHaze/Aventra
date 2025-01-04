package com.furkanharmanc.aventra.map

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ToolTipPopUp(
    modifier: Modifier = Modifier,
    markerInfo: MarkerInfo
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .wrapContentSize(align = Alignment.BottomCenter)
            .padding(16.dp)
    ) {
        Card(
            modifier = Modifier
                .wrapContentSize()
                .padding(8.dp),
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = markerInfo.title, style = MaterialTheme.typography.bodyLarge)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = markerInfo.snippet)
            }
        }
    }
}