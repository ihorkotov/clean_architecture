package com.moto.tablet.ui.main.jobcreation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Divider
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.moto.tablet.R
import com.moto.tablet.ui.component.ImageBackgroundBox
import com.moto.tablet.ui.component.ImageBackgroundButton
import com.moto.tablet.ui.main.Screen
import com.moto.tablet.ui.theme.MotoTabletTheme

@Composable
fun JobOrderCreation(navController: NavController) {
    ImageBackgroundBox(imageResourceId = R.drawable.bg_job_creation) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 160.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ImageBackgroundButton(
                modifier = Modifier.size(width = 521.dp, height = 204.dp),
                imageResourceId = R.drawable.bg_pms,
                title = stringResource(id = R.string.pms).uppercase(),
                subTitle = stringResource(id = R.string.pms_description)
            ) {
                navController.navigate(Screen.PMS.route)
            }

            Divider(thickness = 35.dp, color = Color.Transparent)

            ImageBackgroundButton(
                modifier = Modifier
                    .size(width = 521.dp, height = 204.dp),
                imageResourceId = R.drawable.bg_parts_installation,
                title = stringResource(id = R.string.parts_installation)
            ) {
                navController.navigate(Screen.SearchProduct.route)
            }

        }
    }
}

@Preview("job creation screen", widthDp = 768, heightDp = 1024)
@Composable
fun JobOrderCreationPreview() {
    MotoTabletTheme {
        Surface {
            JobOrderCreation(rememberNavController())
        }
    }
}