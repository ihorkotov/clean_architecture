package com.moto.tablet.ui.component

import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun LoadingProgressBar(modifier: Modifier) {
    CircularProgressIndicator(
        modifier = modifier
            .width(64.dp)
    )
}