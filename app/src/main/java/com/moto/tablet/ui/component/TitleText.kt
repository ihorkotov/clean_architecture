package com.moto.tablet.ui.component

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.moto.tablet.ui.theme.OpenSansFontFamily

@Composable
fun TitleText(
    modifier: Modifier,
    title: String
) {
    val offset = Offset(5.0f, 10.0f)
    Text(
        modifier = modifier,
        text = title,
        color = Color.White,
        fontFamily = OpenSansFontFamily,
        fontSize = 40.sp,
        fontWeight = FontWeight.Bold,
        style = TextStyle(
            shadow = Shadow(
                color = Color.DarkGray, offset = offset, blurRadius = 4f
            )
        )
    )
}