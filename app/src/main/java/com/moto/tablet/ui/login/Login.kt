package com.moto.tablet.ui.login

import android.app.Activity
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Shapes
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.moto.tablet.R
import com.moto.tablet.ui.main.MainActivity
import com.moto.tablet.ui.theme.MotoTabletTheme
import com.moto.tablet.ui.theme.NavButtonColor

@Composable
fun Login(
    navController: NavController
) {
    val viewModel: LoginViewModel = hiltViewModel()
    LoginMain(
        navController = navController,
        userName = viewModel.username,
        password = viewModel.password,
        onUserNameUpdated = {
            viewModel.updateUsername(it)
        },
        onPasswordUpdated = {
            viewModel.updatePassword(it)
        },
        onClickLogin = {
            viewModel.login()
        }
    )
    if (viewModel.goToNextPage) {
        val activity = LocalContext.current as? Activity
        activity?.startActivity(Intent(activity, MainActivity::class.java))
        activity?.finish()
        viewModel.goToNextPage = false
    }
}

@Composable
fun LoginMain(
    navController: NavController,
    userName: String,
    password: String,
    onUserNameUpdated: (name: String) -> Unit,
    onPasswordUpdated: (name: String) -> Unit,
    onClickLogin: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
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
            text = stringResource(id = R.string.user_login).uppercase(),
            style = TextStyle(
                fontSize = 18.sp,
                fontFamily = FontFamily(Font(R.font.opensans_bold)),
                fontWeight = FontWeight.Bold
            ),
        )

        UserNameUI(userName, onUserNameUpdated)
        PasswordUI(password, onPasswordUpdated)

        TextButton(
            modifier = Modifier.padding(start = 50.dp),
            onClick = {
                navController.navigate(LoginScreen.ForgetPassword.route)
            }
        ) {
            Text(
                stringResource(R.string.forget_password).uppercase(),
                style = MaterialTheme.typography.bodySmall,
                color = NavButtonColor
            )
        }

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)
                .padding(horizontal = 80.dp)
                .height(52.dp),
            shape = Shapes().medium,
            onClick = onClickLogin
        ) {
            Text(
                text = stringResource(id = R.string.login).uppercase(),
                style = MaterialTheme.typography.labelLarge
            )
        }
    }
}
@Composable
fun UserNameUI(
    userName: String,
    onUserNameUpdated: (name: String) -> Unit
) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 66.dp, top = 30.dp, bottom = 5.dp),
        text = stringResource(id = R.string.user_name).uppercase(),
        style = MaterialTheme.typography.bodySmall,
        color = Color.Gray
    )

    OutlinedTextField(
        value = userName,
        onValueChange = onUserNameUpdated,
        placeholder = { Text(stringResource(id = R.string.enter_user_name),
            color = Color.LightGray) },
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 60.dp)
    )
}

@Composable
fun PasswordUI(
    password: String,
    onPasswordUpdated: (name: String) -> Unit
) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 66.dp, top = 20.dp, bottom = 5.dp),
        text = stringResource(id = R.string.password).uppercase(),
        style = MaterialTheme.typography.bodySmall,
        color = Color.Gray
    )

    OutlinedTextField(
        value = password,
        onValueChange = onPasswordUpdated,
        placeholder = { Text(stringResource(id = R.string.enter_password),
            color = Color.LightGray) },
        visualTransformation = PasswordVisualTransformation(),
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 60.dp)
    )
}

@Preview("Login", widthDp = 768, heightDp = 1024)
@Composable
fun PreviewLogin() {
    MotoTabletTheme {
        Surface {
            LoginMain(
                navController = rememberNavController(),
                userName = "viewModel.username",
                password = "viewModel.password",
                onUserNameUpdated = {},
                onPasswordUpdated = {},
                onClickLogin = {},
            )
        }
    }
}