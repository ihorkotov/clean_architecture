package com.moto.tablet.ui.main.jobcreation.searchresult

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.moto.tablet.R
import com.moto.tablet.model.PartProduct
import com.moto.tablet.model.Stock
import com.moto.tablet.model.fakePartProduct
import com.moto.tablet.ui.component.BackButton
import com.moto.tablet.ui.component.ImageBackgroundBox
import com.moto.tablet.ui.component.TitleText
import com.moto.tablet.ui.theme.MotoTabletTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PartProductDetail(
    navController: NavController,
    product: PartProduct?
) {
    product ?: return
    ImageBackgroundBox(imageResourceId = R.drawable.bg_pms_detail) {
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
                        append(stringResource(id = R.string.available_stocks_for))
                        append(" ")
                        withStyle(
                            style = SpanStyle(
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                        ) {
                            append(product.productName)
                        }
                    },
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.LightGray
                )

                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 85.dp)
                        .padding(top = 40.dp)
                        .weight(1f),
                    contentPadding = PaddingValues(bottom = 16.dp)
                ) {
                    stickyHeader {
                        StockHeaderItem()
                    }

                    items(product.stocks) {
                        ProductStockItem(stock = it)
                        Divider(modifier = Modifier.padding(top = 12.dp))
                    }
                }
            }

            Spacer(modifier = Modifier.height(120.dp))
        }
    }
}

@Composable
fun StockHeaderItem() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp)
            .height(36.dp)
            .background(Color.White),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier
                .padding(start = 24.dp)
                .weight(1f),
            text = stringResource(id = R.string.location).uppercase(),
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            color = Color.LightGray,
        )

        Text(
            modifier = Modifier
                .padding(end = 40.dp)
                .width(45.dp),
            text = stringResource(id = R.string.qty).uppercase(),
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            color = Color.LightGray,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun ProductStockItem(
    stock: Stock
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 12.dp)
            .height(36.dp)
            .background(Color.White),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier
                .padding(start = 24.dp)
                .weight(1f),
            text = stock.location,
            fontSize = 16.sp,
        )

        Text(
            modifier = Modifier
                .padding(end = 40.dp)
                .width(45.dp),
            text = stock.quantity.toString(),
            fontSize = 16.sp,
            textAlign = TextAlign.Center
        )
    }
}

@Preview("product detail preview", widthDp = 768, heightDp = 1024)
@Composable
fun PartProductDetailPreview() {
    MotoTabletTheme {
        ImageBackgroundBox(imageResourceId = R.drawable.bg_pms_detail) {
            PartProductDetail(
                navController = rememberNavController(),
                product = fakePartProduct()
            )
        }
    }
}

@Preview("sticky header item", widthDp = 768, heightDp = 45, showBackground = true)
@Composable
fun StockHeaderItemPreview() {
    MotoTabletTheme {
        StockHeaderItem()
    }
}