package com.moto.tablet.ui.main.jobcreation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.moto.tablet.R
import com.moto.tablet.model.PMS
import com.moto.tablet.ui.component.BackButton
import com.moto.tablet.ui.component.ImageBackgroundBox
import com.moto.tablet.ui.component.ImageBackgroundButton
import com.moto.tablet.ui.main.Screen
import com.moto.tablet.ui.theme.MotoTabletTheme
import com.moto.tablet.ui.theme.OpenSansFontFamily

@Composable
fun PMS(navController: NavController) {
    ImageBackgroundBox(imageResourceId = R.drawable.bg_pms_main) {
        BackButton(
            modifier = Modifier.padding(top = 62.dp, start = 32.dp)
        ) {
            navController.navigateUp()
        }
        Column(
            modifier = Modifier
                .padding(top = 30.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(id = R.string.pms),
                color = Color.White,
                fontFamily = OpenSansFontFamily,
                fontSize = 48.sp,
                fontWeight = FontWeight.Bold
            )
            
            Text(
                text = stringResource(id = R.string.pms_description),
                color = Color.White,
                fontFamily = OpenSansFontFamily,
                fontSize = 20.sp,
                fontWeight = FontWeight.Light
            )
        }

        Column(
            modifier = Modifier.fillMaxSize().padding(vertical = 165.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            ImageBackgroundButton(
                modifier = Modifier.size(width = 521.dp, height = 204.dp),
                imageResourceId = R.drawable.bg_scooter,
                title = stringResource(id = R.string.scooter).uppercase()
            ) {
                navController.navigate("${Screen.PMSDetail.route}/${PMS.Scooter.name}")
            }

            ImageBackgroundButton(
                modifier = Modifier.size(width = 521.dp, height = 204.dp),
                imageResourceId = R.drawable.bg_big_bikes,
                title = stringResource(id = R.string.big_bikes).uppercase()
            ) {
                navController.navigate("${Screen.PMSDetail.route}/${PMS.BigBikes.name}")
            }

            ImageBackgroundButton(
                modifier = Modifier.size(width = 521.dp, height = 204.dp),
                imageResourceId = R.drawable.bg_underbone,
                title = stringResource(id = R.string.underbone).uppercase()
            ) {
                navController.navigate("${Screen.PMSDetail.route}/${PMS.Underbone.name}")
            }
        }

    }
}




@Preview("pms screen", widthDp = 768, heightDp = 1024)
@Composable
fun PMSPreview() {
    MotoTabletTheme {
        Surface {
            PMS(rememberNavController())
        }
    }
}