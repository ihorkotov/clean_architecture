package com.moto.tablet.ui.main.jobcreation.searchresult

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.moto.tablet.R
import com.moto.tablet.model.PartProduct
import com.moto.tablet.model.fakePartProduct
import com.moto.tablet.model.fakePartProducts
import com.moto.tablet.ui.component.BackButton
import com.moto.tablet.ui.component.ImageBackgroundBox
import com.moto.tablet.ui.component.LoadingProgressBar
import com.moto.tablet.ui.component.TitleText
import com.moto.tablet.ui.main.Screen
import com.moto.tablet.ui.theme.Blue
import com.moto.tablet.ui.theme.Green
import com.moto.tablet.ui.theme.InterFontFamily
import com.moto.tablet.ui.theme.MotoTabletTheme
import com.moto.tablet.ui.theme.OpenSansFontFamily
import com.moto.tablet.ui.theme.Orange
import com.moto.tablet.util.currencyString

@Composable
fun SearchProductResult(navController: NavController) {
    ImageBackgroundBox(imageResourceId = R.drawable.bg_pms_detail) {
        val viewModel: SearchProductResultViewModel = hiltViewModel()
        val cartItemCount by viewModel.cartTotalCountWithoutLabor.collectAsState(0)
        val cartTotalPrice by viewModel.cartTotalPriceWithoutLabor.collectAsState(0f)
        val products = viewModel.products
        SearchProductResultMain(
            navController = navController,
            cartItemCount = cartItemCount,
            cartTotalPrice = cartTotalPrice,
            query = viewModel.query,
            products = products,
            onClickViewImages = {
                viewModel.updateFullScreeImageList(it)
                navController.navigate(Screen.FullScreenImages.route)
            },
            onClickAddItem = {
                viewModel.addPartProductToCart(it, 1)
            },
            onClickProduct = {
                viewModel.selectProductDetail(it)
                navController.navigate(Screen.ProductDetail.route)
            }
        )
        val isLoading = viewModel.showProgress
        if (isLoading) {
            LoadingProgressBar(modifier = Modifier.align(Alignment.Center))
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SearchProductResultMain(
    navController: NavController,
    cartItemCount: Int,
    cartTotalPrice: Float,
    query: String,
    products: List<PartProduct>,
    onClickViewImages: (images: List<String>) -> Unit,
    onClickAddItem: (product: PartProduct) -> Unit,
    onClickProduct: (product: PartProduct) -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 62.dp),
        ) {
            BackButton(
                modifier = Modifier
                    .padding(start = 32.dp)
                    .align(Alignment.CenterStart)
            ) {
                navController.navigateUp()
            }

            TitleText(
                modifier = Modifier.align(Alignment.Center),
                title = stringResource(id = R.string.products).uppercase(),
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .padding(top = 20.dp)
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(size = 10.dp)
                )
                .weight(1f)
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 30.dp)
                    .padding(horizontal = 30.dp),
                text = buildAnnotatedString {
                    append(stringResource(id = R.string.search_result_for))
                    append(" ")
                    withStyle(
                        style = SpanStyle(
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                    ) {
                        append(query)
                    }
                },
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                color = Color.LightGray
            )

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp)
                    .padding(top = 30.dp)
                    .weight(1f),
                contentPadding = PaddingValues(bottom = 16.dp)
            ) {
                stickyHeader {
                    TitleItem()
                }

                items(products) {
                    ProductItem(
                        product = it,
                        onClickViewImages = {
                            onClickViewImages(it.images)
                        },
                        onClickAddItem = {
                            onClickAddItem(it)
                        },
                        onClickProduct = {
                            onClickProduct(it)
                        }
                    )
                    Divider(modifier = Modifier.padding(top = 16.dp))
                }
            }
        }

        Spacer(modifier = Modifier.height(120.dp))

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
                onClick = {
                    navController.navigate(Screen.Profile.route)
//                    navController.navigate(Screen.Checkout.route)
                },
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
fun TitleItem() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(45.dp)
            .background(Color.White),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.weight(0.17f),
            text = stringResource(id = R.string.sku).uppercase(),
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            color = Color.LightGray,
            textAlign = TextAlign.Center
        )

        Text(
            modifier = Modifier.weight(0.29f),
            text = stringResource(id = R.string.product_name).uppercase(),
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            color = Color.LightGray,
            textAlign = TextAlign.Center
        )

        Text(
            modifier = Modifier.weight(0.12f),
            text = stringResource(id = R.string.qty).uppercase(),
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            color = Color.LightGray,
            textAlign = TextAlign.Center
        )

        Text(
            modifier = Modifier.weight(0.18f),
            text = stringResource(id = R.string.price).uppercase(),
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            color = Color.LightGray,
            textAlign = TextAlign.Center
        )

        Text(
            modifier = Modifier.weight(0.22f),
            text = stringResource(id = R.string.actions).uppercase(),
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            color = Color.LightGray,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun ProductItem(
    product: PartProduct,
    onClickViewImages: () -> Unit,
    onClickAddItem: () -> Unit,
    onClickProduct: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
            .height(45.dp)
            .clickable(
                role = Role.Button,
                onClick = onClickProduct
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.weight(0.17f).padding(8.dp),
            text = product.sku,
            fontSize = 16.sp,
        )

        Text(
            modifier = Modifier.weight(0.29f),
            text = product.productName,
            fontSize = 16.sp,
            textAlign = TextAlign.Center
        )

        Text(
            modifier = Modifier.weight(0.12f),
            text = product.quantity.toString(),
            fontSize = 16.sp,
            textAlign = TextAlign.Center
        )

        Text(
            modifier = Modifier.weight(0.18f),
            text = currencyString(product.price),
            fontSize = 16.sp,
            textAlign = TextAlign.Center
        )

        Row(
            modifier = Modifier.weight(0.22f),
            horizontalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.width(16.dp))
            Button(
                modifier = Modifier.size(45.dp),
                contentPadding = PaddingValues(0.dp),
                shape = RoundedCornerShape(5.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Green,
                ),
                onClick = onClickViewImages
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_gallery),
                    contentDescription = "image",
                    tint = Color.White
                )
            }
            Spacer(modifier = Modifier.width(4.dp))
            Button(
                modifier = Modifier
                    .width(78.dp)
                    .height(45.dp),
                contentPadding = PaddingValues(0.dp),
                shape = RoundedCornerShape(5.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Blue
                ),
                onClick = onClickAddItem
            ) {
                Text(text = stringResource(id = R.string.add).uppercase())
            }
        }
    }
}

@Preview("search product result preview", widthDp = 768, heightDp = 1024)
@Composable
fun SearchProductPreview() {
    MotoTabletTheme {
        ImageBackgroundBox(imageResourceId = R.drawable.bg_pms_detail) {
            SearchProductResultMain(
                navController = rememberNavController(),
                cartItemCount = 5,
                cartTotalPrice = 1300f,
                query = "Brand for",
                products = fakePartProducts(),
                onClickViewImages = {},
                onClickAddItem = {},
                onClickProduct = {}
            )
        }
    }
}

@Preview("title item", widthDp = 768, heightDp = 45, showBackground = true)
@Composable
fun TitleItemPreview() {
    MotoTabletTheme {
        TitleItem()
    }
}

@Preview("product item", widthDp = 768, heightDp = 65, showBackground = true)
@Composable
fun ProductItemPreview() {
    MotoTabletTheme {
        ProductItem(fakePartProduct(), {}, {}, {})
    }
}