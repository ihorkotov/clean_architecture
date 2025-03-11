package com.moto.tablet.ui.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate

@Composable
fun DropDownTrailingIcon(expanded: Boolean) {
    Icon(
        Icons.Filled.KeyboardArrowDown,
        null,
        Modifier.rotate(if (expanded) 180f else 0f)
    )
}