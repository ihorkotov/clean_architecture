package com.moto.tablet.ui.main.jobcreation.packagedetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.moto.tablet.R
import com.moto.tablet.model.PMSPackage
import com.moto.tablet.model.PMSPackageContent
import com.moto.tablet.model.fakePackage
import com.moto.tablet.ui.component.BackButton
import com.moto.tablet.ui.component.ImageBackgroundBox
import com.moto.tablet.ui.component.QuantityCount
import com.moto.tablet.ui.main.Screen
import com.moto.tablet.ui.theme.Blue
import com.moto.tablet.ui.theme.BorderColor
import com.moto.tablet.ui.theme.Green
import com.moto.tablet.ui.theme.MotoTabletTheme
import com.moto.tablet.ui.theme.OpenSansFontFamily
import com.moto.tablet.util.currencyString

@Composable
fun PackageDetail(navController: NavController) {
    ImageBackgroundBox(imageResourceId = R.drawable.bg_pms_detail) {
        val viewModel: PackageDetailViewModel = hiltViewModel()
        val pmsPackage = viewModel.selectedPMSPackage() ?: return@ImageBackgroundBox
        PackageDetailMain(
            navController = navController,
            pmsPackage = pmsPackage,
            addToCart = { count ->
                viewModel.addPackageToCart(pmsPackage, count)
            }
        )
    }
}

@Composable
fun PackageDetailMain(
    navController: NavController,
    pmsPackage: PMSPackage,
    addToCart: (count: Int) -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(260.dp)
        ) {
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = painterResource(id = R.drawable.bg_package_large),
                contentDescription = "",
                contentScale = ContentScale.Crop
            )
            BackButton(
                modifier = Modifier.padding(top = 62.dp, start = 32.dp)
            ) {
                navController.navigateUp()
            }
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 12.dp, start = 60.dp, end = 60.dp)
                .background(Color.White)
        ) {
            Text(
                text = "Package 1",
                fontFamily = OpenSansFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(vertical = 20.dp)
            )
            Row(
                modifier = Modifier.fillMaxSize()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(0.32f)
                        .padding(horizontal = 25.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(id = pmsPackage.statusString),
                        fontFamily = OpenSansFontFamily,
                        fontWeight = FontWeight.Normal,
                        fontSize = 16.sp,
                        color = Green
                    )

                    Text(
                        modifier = Modifier
                            .padding(top = 16.dp, bottom = 12.dp)
                            .fillMaxWidth(),
                        text = currencyString(pmsPackage.price),
                        fontFamily = OpenSansFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp,
                        textAlign = TextAlign.Center
                    )

                    var count by remember { mutableIntStateOf(0) }
                    QuantityCount(
                        modifier = Modifier
                            .height(55.dp)
                            .fillMaxWidth(),
                        count = count,
                        decreaseItemCount = { count = maxOf(0, count - 1) } ,
                        increaseItemCount = { count += 1 },
                    )
                    var showCheckOut by remember { mutableStateOf(false) }
                    val buttonColor = if (showCheckOut) Green else Blue
                    val titleResource = if (showCheckOut) R.string.checkout else R.string.add_to_cart
                    Button(
                        onClick = {
                            if (showCheckOut) {
                                navController.navigate(Screen.Checkout.route)
                            } else {
                            if (count > 0) {
                                addToCart(count)
                                showCheckOut = true
                            }
                        } },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = buttonColor
                        ),
                        modifier = Modifier
                            .padding(top = 20.dp)
                            .fillMaxWidth(),
                        shape = RoundedCornerShape(4.dp),
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Text(
                            text = stringResource(id = titleResource).uppercase(),
                            fontFamily = OpenSansFontFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp,
                        )
                    }
                }

                Divider(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(1.dp)
                        .padding(bottom = 16.dp),
                    color = BorderColor
                )

                LazyColumn(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(0.68f),
                    contentPadding = PaddingValues(16.dp)
                ) {
                    items(pmsPackage.contentList) {
                        PackageContentItem(
                            modifier = Modifier.fillMaxWidth().height(36.dp),
                            content = it
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun PackageContentItem(
    modifier: Modifier,
    content: PMSPackageContent,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier.padding(start = 16.dp),
            painter = painterResource(id = R.drawable.ic_arrow_right_black_background),
            contentDescription = ""
        )
        Text(
            modifier = Modifier.padding(start = 8.dp),
            text = content.title,
            fontFamily = OpenSansFontFamily,
            fontSize = 16.sp
        )
    }
}

@Preview("pms detail", widthDp = 768, heightDp = 1024)
@Composable
fun PackageDetailPreview() {
    MotoTabletTheme {
        ImageBackgroundBox(imageResourceId = R.drawable.bg_pms_detail) {
            PackageDetailMain(
                rememberNavController(),
                fakePackage(),
            ) { }
        }
    }
}

@Preview("Package content item", widthDp = 200, heightDp = 32,
    backgroundColor = 0xFFFFFFFF, showBackground = true)
@Composable
fun PackageContentItemPreview() {
    MotoTabletTheme {
        PackageContentItem(
            Modifier,
            PMSPackageContent(1,"Change Oil", "Change Oil description")
        )
    }
}
