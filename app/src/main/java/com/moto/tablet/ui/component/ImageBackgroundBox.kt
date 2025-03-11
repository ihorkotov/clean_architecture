package com.moto.tablet.ui.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.moto.tablet.R
import com.moto.tablet.ui.theme.MotoTabletTheme
import com.moto.tablet.ui.theme.OpenSansFontFamily

const val ImageBackgroundButtonAlpha = 0.6f
const val ImageBackgroundFilterAlpha = 0.15f

@Composable
fun ImageBackgroundBox(
    @DrawableRes imageResourceId: Int,
    content: @Composable BoxScope.() -> Unit
) {
    MotoScaffold {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = painterResource(id = imageResourceId),
                contentDescription = "background image",
                contentScale = ContentScale.Crop
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .alpha(ImageBackgroundFilterAlpha)
                    .background(Color.Black),
            )
            content()
        }
    }
}

@Composable
fun ImageBackgroundButton(
    modifier: Modifier,
    @DrawableRes imageResourceId: Int,
    title: String,
    subTitle: String? = null,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .clip(shape = RoundedCornerShape(20.dp))
            .background(Color.Black)
            .paint(
                painter = painterResource(id = imageResourceId),
                contentScale = ContentScale.Crop,
                alpha = ImageBackgroundButtonAlpha
            )
            .clickable(
                onClick = onClick,
                role = Role.Button
            )

    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = title,
                color = Color.White,
                fontFamily = OpenSansFontFamily,
                fontSize = 48.sp,
                fontWeight = FontWeight.Bold
                )
            if (subTitle != null) {
                Text(
                    text = subTitle,
                    color = Color.White,
                    fontFamily = OpenSansFontFamily,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Light
                    )
            }
        }
    }

}

@Preview("show image background ", widthDp = 768, heightDp = 1024)
@Composable
fun ShowImageBackground() {
    MotoTabletTheme {
        ImageBackgroundBox(imageResourceId = R.drawable.bg_job_creation) {}
    }
}

@Preview("show image button ", widthDp = 521, heightDp = 204)
@Composable
fun ShowImageBackgroundButton() {
    MotoTabletTheme {
        ImageBackgroundButton(
            modifier = Modifier.size(width = 521.dp, height = 204.dp),
            imageResourceId = R.drawable.bg_pms,
            title = "P.M.S",
            subTitle = "Preventive Maintenance Schedule",
            onClick = {}
        )
    }
}

