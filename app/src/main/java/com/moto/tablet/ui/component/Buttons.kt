package com.moto.tablet.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moto.tablet.R
import com.moto.tablet.ui.theme.MotoTabletTheme
import com.moto.tablet.ui.theme.NavButtonColor

@Composable
fun BackButton(
    modifier: Modifier,
    onClick: () -> Unit
) {
    OutlinedButton(
        modifier = modifier
            .size(width = 126.dp, height = 50.dp)
            .border(width = 2.dp, color = Color.White, shape = RoundedCornerShape(50)),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = NavButtonColor,
            contentColor = Color.White
        ),
        onClick = onClick
    ) {
        Image(painter = painterResource(id = R.drawable.ic_arrow_left), contentDescription = "")
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = stringResource(id = R.string.back).uppercase())
    }
}

@Preview
@Composable
fun BackButtonPreview() {
    MotoTabletTheme {
        BackButton(Modifier) { }
    }
}