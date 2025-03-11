package com.moto.tablet.ui.component

import android.text.format.DateUtils
import android.text.format.DateUtils.FORMAT_SHOW_DATE
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.moto.tablet.ui.theme.MotoTabletTheme
import com.moto.tablet.ui.theme.TimerBackgroundColor
import com.moto.tablet.util.timeString
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun DateTimeShow(
    modifier: Modifier,
    isSmall: Boolean = false
) {
    Column(
        modifier = modifier
            .background(
                color = TimerBackgroundColor,
                shape = RoundedCornerShape(20.dp)
            ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val dateFontSize = if (isSmall) {
            12.sp
        } else {
            15.sp
        }
        val timeFontSize = if (isSmall) {
            25.sp
        } else {
            35.sp
        }

        var timeMilliseconds by remember { mutableLongStateOf(System.currentTimeMillis()) }

        DisposableEffect(Unit) {
            val scope = CoroutineScope(Dispatchers.Default)
            val job = scope.launch {
                while (true) {
                    delay(1000)
                    timeMilliseconds = System.currentTimeMillis()
                }
            }
            onDispose {
                job.cancel()
            }
        }
        val context = LocalContext.current
        Text(
            text = DateUtils.formatDateTime(context, timeMilliseconds, FORMAT_SHOW_DATE),
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = dateFontSize
        )

        Text(
            text = timeMilliseconds.timeString(),
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = timeFontSize
        )
    }
}

@Preview("DateTimeShow preview", widthDp = 324, heightDp = 100)
@Composable
fun DateTimeShowPreview() {
    MotoTabletTheme {
        DateTimeShow(modifier = Modifier)
    }
}