package com.moto.tablet.ui.main.jobcreation.pmsdetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.moto.tablet.R
import com.moto.tablet.model.PMS
import com.moto.tablet.model.PMSPackage
import com.moto.tablet.model.fakePackage
import com.moto.tablet.model.fakePackages
import com.moto.tablet.ui.component.BackButton
import com.moto.tablet.ui.component.ImageBackgroundBox
import com.moto.tablet.ui.component.ImageBackgroundButton
import com.moto.tablet.ui.component.QuantityCount
import com.moto.tablet.ui.main.Screen
import com.moto.tablet.ui.theme.Blue
import com.moto.tablet.ui.theme.Green
import com.moto.tablet.ui.theme.InterFontFamily
import com.moto.tablet.ui.theme.MotoTabletTheme
import com.moto.tablet.ui.theme.NavButtonColor
import com.moto.tablet.ui.theme.OpenSansFontFamily
import com.moto.tablet.ui.theme.Orange
import com.moto.tablet.util.currencyString

const val PARAM_PMS_DETAIL_TYPE = "PMSDetailType"

@Composable
fun PMSDetail(pmsType: PMS, navController: NavController) {
    ImageBackgroundBox(imageResourceId = R.drawable.bg_pms_detail) {
        val pmsDetailViewModel: PMSDetailViewModel = hiltViewModel()
        pmsDetailViewModel.refreshOrders()

        val cartItemCount by pmsDetailViewModel.cartTotalCountWithoutLabor.collectAsState(0)
        val cartTotalPrice by pmsDetailViewModel.cartTotalPriceWithoutLabor.collectAsState(0f)
        PMSDetailMain(
            navController = navController,
            pmsType = pmsType,
            packages = pmsDetailViewModel.getPackagesWithType(pmsType),
            cartItemCount = cartItemCount,
            cartTotalPrice = cartTotalPrice,
            addToCart = { pmsPackage, count ->
                pmsDetailViewModel.addPackageToCart(pmsPackage, count)
            },
            packageClick = {
                pmsDetailViewModel.savePMSPackageForDetailPageNavigate(it)
                navController.navigate(Screen.PackageDetail.route)
            }
        )
    }
}

@Composable
fun PMSDetailMain(
    navController: NavController,
    pmsType: PMS,
    packages: List<PMSPackage>,
    cartItemCount: Int,
    cartTotalPrice: Float,
    addToCart: (pmsPackage: PMSPackage, count: Int) -> Unit,
    packageClick: (pmsPackage: PMSPackage) -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 37.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            BackButton(
                modifier = Modifier
            ) {
                navController.navigateUp()
            }

            val stringResource: Int
            val imageResource = when (pmsType) {
                PMS.Scooter -> {
                    stringResource = R.string.scooter
                    R.drawable.bg_scooter
                }
                PMS.BigBikes -> {
                    stringResource = R.string.big_bikes
                    R.drawable.bg_big_bikes
                }
                PMS.Underbone -> {
                    stringResource = R.string.underbone
                    R.drawable.bg_underbone
                }
            }
            ImageBackgroundButton(
                modifier = Modifier.size(width = 521.dp, height = 116.dp),
                imageResourceId = imageResource,
                title = stringResource(id = stringResource).uppercase()
            ) {
            }
        }

        val screenWidth = LocalConfiguration.current.screenWidthDp.dp
        var isWideFor3Items = false
        if (screenWidth >= 224.dp * 4) {
            isWideFor3Items = true
        }

        val modifier = if (isWideFor3Items) {
            Modifier.width(800.dp)
        } else {
            Modifier.fillMaxWidth()
        }
        PackageGrid(
            modifier = modifier
                .weight(1f)
                .padding(top = 75.dp),
            packages = packages,
            addToCart = addToCart,
            packageClick = packageClick
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .background(Color.White)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(0.23f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = pluralStringResource(id = R.plurals.items, count = cartItemCount),
                    fontFamily = OpenSansFontFamily,
                    fontWeight = FontWeight.Light,
                    fontSize = 12.sp
                )
                Text(
                    text = cartItemCount.toString(),
                    fontFamily = OpenSansFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 30.sp
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(0.5f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(id = R.string.amount),
                    fontFamily = OpenSansFontFamily,
                    fontWeight = FontWeight.Light,
                    fontSize = 12.sp
                )
                Text(
                    text = currencyString(cartTotalPrice),
                    fontFamily = OpenSansFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 30.sp
                )
            }

            Button(
                onClick = { navController.navigate(Screen.Checkout.route) },
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(0.25f),
                shape = RectangleShape,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Orange
                )
            ) {
                Text(
                    text = stringResource(id = R.string.checkout).uppercase(),
                    fontFamily = InterFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            }
        }
    }
}

@Composable
fun PackageGrid(
    modifier: Modifier,
    packages: List<PMSPackage>,
    addToCart: (pmsPackage: PMSPackage, count: Int) -> Unit,
    packageClick: (pmsPackage: PMSPackage) -> Unit
) {
    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.FixedSize(224.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalArrangement = Arrangement.spacedBy(24.dp),
        contentPadding = PaddingValues(bottom = 24.dp)
    ) {
        items(packages) {
            PackageItem(
                item = it,
                addToCart = addToCart,
                packageClick = packageClick
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PackageItem(
    item: PMSPackage,
    addToCart: (pmsPackage: PMSPackage, count: Int) -> Unit,
    packageClick: (pmsPackage: PMSPackage) -> Unit
) {
    Card(
        modifier = Modifier
            .width(224.dp)
            .height(264.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        onClick = {
            packageClick(item)
        }
    ) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(height = 48.dp)
            .background(NavButtonColor),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = item.title,
                fontFamily = OpenSansFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = Color.White
            )
        }

        Image(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1.86f),
            painter = painterResource(id = item.image),
            contentDescription = "package image",
            contentScale = ContentScale.Crop
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 12.dp, top = 10.dp, end = 26.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = currencyString(item.price),
                fontFamily = OpenSansFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )

            Text(
                text = stringResource(id = item.statusString),
                fontFamily = OpenSansFontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp,
                color = Green
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(bottom = 10.dp, start = 8.dp, end = 8.dp, top = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            var count by remember { mutableIntStateOf(0) }

            QuantityCount(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
                    .padding(end = 8.dp),
                count = count,
                decreaseItemCount = { count = maxOf(0, count - 1) } ,
                increaseItemCount = { count += 1 },
                isSmall = true
            )
            Button(
                onClick = {
                    if (count > 0) {
                        addToCart(item, count)
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Blue
                ),
                modifier = Modifier
                    .weight(1f),
                shape = RoundedCornerShape(4.dp),
                contentPadding = PaddingValues(0.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.add_to_cart).uppercase(),
                    fontFamily = OpenSansFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                )
            }
        }

    }
}

@Preview("pms detail", widthDp = 768, heightDp = 1024)
@Composable
fun PMSDetailPreview() {
    MotoTabletTheme {
        ImageBackgroundBox(imageResourceId = R.drawable.bg_pms_detail) {
            PMSDetailMain(
                navController = rememberNavController(),
                pmsType = PMS.Scooter,
                packages = fakePackages(),
                cartItemCount = 3,
                cartTotalPrice = 1000000f,
                addToCart = { _, _ ->
                },
                packageClick = { }
            )
        }
    }
}

@Preview("pms detail", widthDp = 224, heightDp = 264)
@Composable
fun PackageItemPreview() {
    MotoTabletTheme {
        PackageItem(
            item = fakePackage(),
            addToCart = { _, _ ->
            },
            packageClick = { }
        )
    }
}
