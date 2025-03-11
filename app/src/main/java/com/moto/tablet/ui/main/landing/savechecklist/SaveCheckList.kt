package com.moto.tablet.ui.main.landing.savechecklist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.moto.tablet.R
import com.moto.tablet.model.Order
import com.moto.tablet.model.fakeOrder3
import com.moto.tablet.ui.component.BackButton
import com.moto.tablet.ui.component.CustomerInfo
import com.moto.tablet.ui.component.DateTimeShow
import com.moto.tablet.ui.component.ImageBackgroundBox
import com.moto.tablet.ui.component.TitleText
import com.moto.tablet.ui.main.Screen
import com.moto.tablet.ui.main.landing.KEY_ORDER_STATUS_UPDATED
import com.moto.tablet.ui.theme.BlackTextColor
import com.moto.tablet.ui.theme.Blue
import com.moto.tablet.ui.theme.BorderColor
import com.moto.tablet.ui.theme.Color_A6A6A6
import com.moto.tablet.ui.theme.Color_EDECEC
import com.moto.tablet.ui.theme.CountButtonColor
import com.moto.tablet.ui.theme.InterFontFamily
import com.moto.tablet.ui.theme.MotoTabletTheme

@Composable
fun SaveCheckList(navController: NavController) {
    ImageBackgroundBox(imageResourceId = R.drawable.bg_pms_detail) {
        val viewModel: SaveCheckListViewModel = hiltViewModel()
        val order = viewModel.order ?: return@ImageBackgroundBox
        SaveCheckListMain(
            navController = navController,
            order = order,
            onClickSave = {
                val isSearchOrdersTab = viewModel.isSearchTab
                val routePageKey = if (isSearchOrdersTab) {
                    Screen.SearchOrders.route
                } else {
                    Screen.Landing.route
                }
                navController.getBackStackEntry(routePageKey).savedStateHandle[KEY_ORDER_STATUS_UPDATED] = true
                navController.popBackStack(routePageKey, inclusive = false)
            }
        )
    }
}

@Composable
fun SaveCheckListMain(
    navController: NavController,
    order: Order,
    onClickSave: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxWidth()
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
                    title = stringResource(id = R.string.check_list).uppercase(),
                )
                DateTimeShow(
                    modifier = Modifier
                        .padding(end = 40.dp)
                        .size(width = 202.dp, height = 72.dp)
                        .align(Alignment.CenterEnd),
                    isSmall = true
                )
            }
            CustomerInfo(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp)
                    .padding(top = 24.dp),
                customer = order.customer
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp)
                    .padding(top = 10.dp)
                    .background(Color.White)
                    .weight(1f),
            ) {
                Text(
                    modifier = Modifier
                        .padding(top = 10.dp, start = 20.dp, end = 40.dp, bottom = 20.dp)
                        .fillMaxWidth(),
                    text = buildAnnotatedString {
                        append(stringResource(id = R.string.job_order).uppercase())
                        append(": ")
                        withStyle(
                            style = SpanStyle(
                                color = BlackTextColor
                            )
                        ) {
                            append("#")
                            append(order.number)
                        }
                    },
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Gray
                )

                Box(
                    modifier = Modifier
                        .padding(top = 20.dp)
                        .size(width = 270.dp, height = 210.dp)
                        .background(
                            color = CountButtonColor,
                            shape = RoundedCornerShape(20.dp)
                        )
                        .align(Alignment.CenterHorizontally)
                ) {
                    Text(
                        modifier = Modifier.align(Alignment.Center),
                        text = stringResource(id = R.string.take_a_picture),
                        color = Color_A6A6A6,
                        fontFamily = InterFontFamily,
                        fontSize = 14.sp,
                    )
                }

                Box(
                    modifier = Modifier
                        .padding(top = 40.dp)
                        .size(width = 400.dp, height = 210.dp)
                        .background(
                            color = Color_EDECEC,
                            shape = RoundedCornerShape(20.dp)
                        )
                        .align(Alignment.CenterHorizontally)
                ) {
                    Text(
                        modifier = Modifier
                            .padding(top = 16.dp)
                            .align(Alignment.TopCenter),
                        text = stringResource(id = R.string.signature_here),
                        color = Color_A6A6A6,
                        fontFamily = InterFontFamily,
                        fontSize = 14.sp,
                    )
                }
            }

            Row(
                modifier = Modifier
                    .padding(bottom = 50.dp, top = 30.dp)
                    .align(Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    modifier = Modifier.size(width = 185.dp, height = 55.dp),
                    shape = RoundedCornerShape(5.dp),
                    colors = ButtonDefaults.buttonColors(
                        contentColor = Color.White,
                        containerColor = Blue,
                        disabledContainerColor = BorderColor,
                        disabledContentColor = Color.Gray
                    ),
//                    enabled = order.status == OrderStatus.NextToService,
                    onClick = onClickSave,
                ) {
                    Text(
                        text = stringResource(id = R.string.save).uppercase()
                    )
                }
            }
        }
    }
}

@Preview("save checklist preview", widthDp = 768, heightDp = 1024)
@Composable
fun SaveCheckListPreview() {
    MotoTabletTheme {
        ImageBackgroundBox(imageResourceId = R.drawable.bg_pms_detail) {
            SaveCheckListMain(
                rememberNavController(),
                fakeOrder3
            ) {}
        }
    }
}