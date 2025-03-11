package com.moto.tablet.ui.main.landing.checklist

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.moto.tablet.R
import com.moto.tablet.ui.theme.BorderColor
import com.moto.tablet.ui.theme.Green
import com.moto.tablet.ui.theme.InterFontFamily
import com.moto.tablet.ui.theme.MotoTabletTheme
import com.moto.tablet.ui.theme.PlaceHolderColor

@Composable
fun EnterMechanicCodeDialog(
    userMechanicCode: String,
    onDismissRequest: () -> Unit,
    onMechanicCodeMatched: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismissRequest,
    ) {
        Box(
            modifier = Modifier
                .size(width = 500.dp, height = 357.dp)
                .background(Color.White, shape = RoundedCornerShape(10.dp))
        ) {
            Column(
                modifier = Modifier.align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier.padding(top = 8.dp),
                    text = stringResource(id = R.string.enter_mechanic_code).uppercase(),
                    fontFamily = InterFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                )
                var inputCode by remember { mutableStateOf("") }
                val requester = FocusRequester()
                OutlinedTextField(
                    modifier = Modifier
                        .padding(top = 30.dp)
                        .focusRequester(requester),
                    value = inputCode,
                    onValueChange = {
                        inputCode = it
                    },
                    placeholder = {
                        Text(
                            text = stringResource(id = R.string.enter_code).uppercase(),
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = PlaceHolderColor
                        )
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.LightGray,
                        unfocusedBorderColor = Color.LightGray,
                    )
                )
                SideEffect {
                    requester.requestFocus()
                }
                Button(
                    modifier = Modifier
                        .padding(top = 48.dp)
                        .size(width = 150.dp, height = 50.dp),
                    shape = RoundedCornerShape(5.dp),
                    colors = ButtonDefaults.buttonColors(
                        contentColor = Color.White,
                        containerColor = Green,
                        disabledContainerColor = BorderColor,
                        disabledContentColor = Color.Gray
                    ),
                    enabled = inputCode == userMechanicCode,
                    onClick = onMechanicCodeMatched,
                ) {
                    Text(
                        text = stringResource(id = R.string.start).uppercase(),
                        fontSize = 12.sp
                    )
                }
            }
            Image(
                modifier = Modifier
                    .size(48.dp)
                    .align(Alignment.TopEnd)
                    .clickable(
                        role = Role.Button
                    ) {
                        onDismissRequest()
                    },
                imageVector = Icons.Default.Close,
                contentDescription = "close",
                contentScale = ContentScale.Inside
            )
        }
    }
}

@Preview("JobCreatedDialog preview", widthDp = 500, heightDp = 400)
@Composable
fun EnterMechanicCodeDialogPreview() {
    MotoTabletTheme {
        EnterMechanicCodeDialog(
            userMechanicCode = "123456",
            onDismissRequest = {},
        ) { }
    }
}