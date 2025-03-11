package com.moto.tablet.ui.login

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.moto.tablet.R
import com.moto.tablet.ui.component.MotoScaffold
import com.moto.tablet.ui.theme.MotoTabletTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MotoTabletTheme {
                MainView()
            }
        }
    }
}

@Composable
fun MainView() {
    val navController = rememberNavController()
    MotoScaffold {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
                .paint(
                    painter = painterResource(id = R.drawable.bg_login),
                    contentScale = ContentScale.FillBounds
                ),
            contentAlignment = Alignment.Center
        ) {
            NavHost(
                navController,
                startDestination = "Login",
                modifier = Modifier
                    .width(434.dp)
                    .height(589.dp)
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(size = 10.dp)
                    ),
            ) {
                composable(LoginScreen.Login.route) {
                    Login(navController)
                }
                composable(LoginScreen.ForgetPassword.route) { ForgetPassword(navController) }
            }
        }
    }
}


@Preview("Login screen", widthDp = 768, heightDp = 1024)
@Composable
fun PreviewLoginScreen() {
    MotoTabletTheme {
        MainView()
    }
}

sealed class LoginScreen(val route: String) {
    data object Login : LoginScreen("login")
    data object ForgetPassword : LoginScreen("forget_password")
}
