package com.moto.tablet.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.moto.tablet.ui.theme.Blue
import com.moto.tablet.ui.theme.BorderColor
import com.moto.tablet.ui.theme.CountButtonColor
import com.moto.tablet.ui.theme.MotoTabletTheme
import com.moto.tablet.ui.theme.OpenSansFontFamily
import com.moto.tablet.ui.theme.TextColor

@Composable
fun QuantityCount(
    modifier: Modifier,
    count: Int,
    decreaseItemCount: () -> Unit,
    increaseItemCount: () -> Unit,
    isSmall: Boolean = false
) {
    val plusSignTextSize = if (isSmall) 12.sp else 18.sp
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = decreaseItemCount,
            colors = ButtonDefaults.buttonColors(
                containerColor = CountButtonColor
            ),
            shape = RoundedCornerShape(topStart = 2.dp, bottomStart = 2.dp),
            contentPadding = PaddingValues(0.dp),
            modifier = Modifier
                .fillMaxHeight()
                .weight(0.28f),
        ) {
            Text(
                text = "-",
                fontFamily = OpenSansFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = plusSignTextSize,
                color = Blue,
                textAlign = TextAlign.Center
            )
        }
        Box(
            modifier = Modifier
                .background(Color.White)
                .border(
                    width = Dp(0.5f),
                    shape = RectangleShape,
                    color = BorderColor
                )
                .weight(0.46f)
                .fillMaxHeight()
        ) {
            Text(
                modifier = Modifier.align(Alignment.Center),
                fontFamily = OpenSansFontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 15.sp,
                color = TextColor,
                text = count.toString(),
            )
        }

        Button(
            onClick = increaseItemCount,
            colors = ButtonDefaults.buttonColors(
                containerColor = CountButtonColor
            ),
            shape = RoundedCornerShape(topEnd = 2.dp, bottomEnd = 2.dp),
            contentPadding = PaddingValues(0.dp),
            modifier = Modifier
                .fillMaxHeight()
                .weight(0.28f),
        ) {
            Text(
                text = "+",
                fontFamily = OpenSansFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = plusSignTextSize,
                color = Blue,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview(widthDp = 100, heightDp = 36)
@Composable
fun QuantityCountPreview() {
    MotoTabletTheme {
        QuantityCount(Modifier, 2, {}, {})
    }
}