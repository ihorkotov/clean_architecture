package com.moto.tablet.ui.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Shapes
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.moto.tablet.R
import com.moto.tablet.ui.theme.MotoTabletTheme

@Composable
fun ForgetPassword(navController: NavController, isPreview: Boolean = false) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            modifier = Modifier
                .padding(top = 36.dp, bottom = 15.dp)
                .width(162.dp)
                .height(162.dp)
                .align(Alignment.CenterHorizontally),
            painter = painterResource(id = R.drawable.ic_app_icon),
            contentDescription = "image description",
            contentScale = ContentScale.FillBounds
        )

        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = stringResource(id = R.string.forget_password_title).uppercase(),
            style = TextStyle(
                fontSize = 18.sp,
                fontFamily = FontFamily(Font(R.font.opensans_bold)),
                fontWeight = FontWeight.Bold
            ),
        )

        if (isPreview) {
            EmailUIPreview()
            SendButtonPreview()
        } else {
            EmailUI()
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally)
                    .padding(horizontal = 80.dp, vertical = 30.dp)
                    .height(52.dp),
                shape = Shapes().medium,
                onClick = {  }
            ) {
                Text(
                    text = stringResource(id = R.string.send_password).uppercase(),
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }
    }
}

@Composable
fun EmailUI() {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 66.dp, top = 30.dp, bottom = 5.dp),
        text = stringResource(id = R.string.email_address).uppercase(),
        style = MaterialTheme.typography.bodySmall,
        color = Color.Gray
    )

    val viewModel: LoginViewModel = viewModel()
    OutlinedTextField(
        value = viewModel.email,
        onValueChange = { viewModel.updateEmail(it) },
        placeholder = { Text(
            stringResource(id = R.string.enter_email),
            color = Color.LightGray) },
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email, imeAction = ImeAction.Send),
        keyboardActions = KeyboardActions(
            onSend = {
                viewModel.sendEmailForgetPassword()
            }
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 60.dp)
    )
}

@Composable
fun EmailUIPreview() {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 66.dp, top = 30.dp, bottom = 5.dp),
        text = stringResource(id = R.string.email_address).uppercase(),
        style = MaterialTheme.typography.bodySmall,
        color = Color.Gray
    )

    OutlinedTextField(
        value = "",
        onValueChange = { },
        placeholder = { Text(
            stringResource(id = R.string.enter_email),
            color = Color.LightGray) },
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 60.dp)
    )
}

@Composable
fun SendButtonPreview() {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 80.dp, vertical = 30.dp)
            .height(52.dp),
        shape = Shapes().medium,
        onClick = {  }
    ) {
        Text(
            text = stringResource(id = R.string.send_password).uppercase(),
            style = MaterialTheme.typography.labelLarge
        )
    }
}

@Preview("ForgetPassword", widthDp = 768, heightDp = 1024)
@Composable
fun PreviewForgetPassword() {
    MotoTabletTheme {
        Surface {
            ForgetPassword(
                rememberNavController(), isPreview = true
            )
        }
    }
}