package com.moto.tablet.ui.component

import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.SnackbarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.moto.tablet.ui.theme.NavButtonColor


@Composable
fun MotoSnackBar(
    snackBarData: SnackbarData,
    modifier: Modifier = Modifier,
    isShowError: Boolean = false
) {
    val containerColor = if (isShowError) {
        NavButtonColor
    } else {
        SnackbarDefaults.color
    }
    val contentColor = if (isShowError) {
        Color.White
    } else {
        SnackbarDefaults.contentColor
    }
    Snackbar(
        snackbarData = snackBarData,
        modifier = modifier,
        containerColor = containerColor,
        contentColor = contentColor
    )
}
