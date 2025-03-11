package com.moto.tablet.ui.main.landing

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.moto.tablet.R
import com.moto.tablet.model.Order
import com.moto.tablet.model.OrderStatus
import com.moto.tablet.model.UserType
import com.moto.tablet.model.fakeOrder3
import com.moto.tablet.model.fakeOrders
import com.moto.tablet.ui.component.DateTimeShow
import com.moto.tablet.ui.component.ImageBackgroundBox
import com.moto.tablet.ui.component.LoadingProgressBar
import com.moto.tablet.ui.main.Screen
import com.moto.tablet.ui.theme.Blue
import com.moto.tablet.ui.theme.BorderColor
import com.moto.tablet.ui.theme.LightGrayColor
import com.moto.tablet.ui.theme.MotoTabletTheme
import com.moto.tablet.ui.theme.Orange
import com.moto.tablet.util.dateString
import com.moto.tablet.util.timeString

const val KEY_ORDER_STATUS_UPDATED = "order_status_updated"
@Composable
fun Landing(navController: NavController) {
    ImageBackgroundBox(imageResourceId = R.drawable.bg_landing) {
        val viewModel: LandingViewModel = hiltViewModel()
        var showRemoveAlertDialog: Order? by remember { mutableStateOf(null) }

        val updatedOrderStatus = navController.currentBackStackEntry?.savedStateHandle?.getLiveData<Boolean>(
            KEY_ORDER_STATUS_UPDATED
        )?.observeAsState()
        updatedOrderStatus?.value?.let {
            if (it) {
                navController.currentBackStackEntry?.savedStateHandle?.set(KEY_ORDER_STATUS_UPDATED, false)
                viewModel.refreshOrders()
            }
        }
        LandingMain(
            orders = viewModel.orders,
            userType = viewModel.userType,
            onClickView = {
                viewModel.updateSelectedCheckList(it)
                navController.navigate(Screen.CheckList.route)
            },
            onClickRemove = {
                showRemoveAlertDialog = it
            }
        )
        val showProcess = viewModel.showProgress
        if (showProcess) {
            LoadingProgressBar(modifier = Modifier.align(Alignment.Center))
        }
        if (showRemoveAlertDialog != null) {
            RemoveAlertDialog(
                onConfirmation = {
                    viewModel.removeCheckList(showRemoveAlertDialog)
                    showRemoveAlertDialog = null
                },
                onDismissRequest = {
                    showRemoveAlertDialog = null
                }
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LandingMain(
    orders: List<Order>,
    userType: UserType,
    onClickView: (order: Order) -> Unit,
    onClickRemove: (order: Order) -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        DateTimeShow(
            modifier = Modifier
                .padding(top = 75.dp)
                .size(width = 324.dp, height = 100.dp)
        )

        LazyColumn(
            modifier = Modifier
                .padding(top = 40.dp, bottom = 184.dp)
                .padding(horizontal = 40.dp)
                .fillMaxSize()
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(10.dp)
                ),
        ) {
            stickyHeader {
                OrderHeaderItem()
            }
            items(orders) {
                OrderItem(
                    order = it,
                    userType = userType,
                    onClickView = onClickView,
                    onClickRemove = onClickRemove
                )
                Divider(
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
        }
    }
}

@Composable
fun OrderHeaderItem(isSearch: Boolean = false) {
    Row(
        modifier = Modifier
            .padding(horizontal = 12.dp)
            .fillMaxWidth()
            .height(60.dp)
            .background(Color.White),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.weight(0.2f),
            text = stringResource(id = R.string.plate).uppercase(),
            color = LightGrayColor,
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        Text(
            modifier = Modifier.weight(0.25f),
            text = stringResource(id = R.string.status).uppercase(),
            color = LightGrayColor,
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        Row(
            modifier = Modifier.weight(0.5f)
        ) {
            if (isSearch) {
                Text(
                    modifier = Modifier.weight(0.5f),
                    text = stringResource(id = R.string.date_time).uppercase(),
                    color = LightGrayColor,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            }
            Text(
                modifier = Modifier.weight(0.5f),
                text = stringResource(id = R.string.actions).uppercase(),
                color = LightGrayColor,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun OrderItem(
    order: Order,
    userType: UserType,
    onClickView: (order: Order) -> Unit,
    onClickRemove: (order: Order) -> Unit,
    isSearch: Boolean = false
) {
    Row(
        modifier = Modifier
            .padding(horizontal = 12.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.weight(0.2f),
            text = order.number,
            fontSize = 16.sp,
            textAlign = TextAlign.Center
        )
        Row(
            modifier = Modifier.weight(0.25f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(id = order.statusStringResource).uppercase(),
                color = order.statusStringColor,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            if (!isSearch && order.startEndTimeRangeString.isNotEmpty()) {
                Column(
                    modifier = Modifier.padding(start = 12.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = order.start?.timeString() ?: "",
                        color = Color.LightGray,
                        fontSize = 10.sp,
                    )
                    Text(
                        text = order.end?.timeString() ?: "",
                        color = Color.LightGray,
                        fontSize = 10.sp,
                    )
                }

            }
        }
        Row(
            modifier = Modifier.weight(0.5f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (isSearch) {
                Column(
                    modifier = Modifier.weight(0.5f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = order.createdAt.dateString(),
                        color = Color.Gray,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                    if (order.startEndTimeRangeString.isNotEmpty()) {
                        Text(
                            text = order.startEndTimeRangeString,
                            color = Color.LightGray,
                            fontSize = 10.sp,
                            textAlign = TextAlign.Center
                        )
                    }
                }

            }
            Row(
                modifier = Modifier.weight(0.5f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    modifier = Modifier
                        .padding(vertical = 16.dp)
                        .width(110.dp),
                    shape = RoundedCornerShape(5.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Blue,
                        contentColor = Color.White
                    ),
                    onClick = {
                        onClickView(order)
                    }
                ) {
                    Text(
                        text = stringResource(id = R.string.view).uppercase(),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                if (!isSearch && userType == UserType.ServiceAdvisor) {
                    Button(
                        modifier = Modifier
                            .padding(vertical = 16.dp)
                            .padding(start = 8.dp)
                            .width(110.dp),
                        shape = RoundedCornerShape(5.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Orange,
                            disabledContainerColor = BorderColor,
                            contentColor = Color.White,
                            disabledContentColor = Color.White
                        ),
                        enabled = order.status == OrderStatus.NextToService,
                        onClick = {
                            onClickRemove(order)
                        }
                    ) {
                        Text(
                            text = stringResource(id = R.string.remove).uppercase(),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun RemoveAlertDialog(
    onConfirmation: () -> Unit,
    onDismissRequest: () -> Unit,
) {
    AlertDialog(
        title = {
            Text(text = stringResource(id = R.string.remove_job_alert_dialog_message))
        },
        onDismissRequest = { },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmation()
                }
            ) {
                Text(stringResource(id = R.string.yes))
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismissRequest
            ) {
                Text(stringResource(id = R.string.no))
            }
        },
    )
}

@Preview("landing screen", widthDp = 768, heightDp = 1024)
@Composable
fun LandingPreview() {
    MotoTabletTheme {
        ImageBackgroundBox(imageResourceId = R.drawable.bg_landing) {
            LandingMain(
                orders = fakeOrders,
                userType = UserType.ServiceAdvisor,
                onClickRemove = {},
                onClickView = {},
            )
        }
    }
}

@Preview("order header item", showBackground = true, widthDp = 666, heightDp = 40)
@Composable
fun OrderHeaderItemPreview() {
    MotoTabletTheme {
        OrderHeaderItem(false)
    }
}

@Preview("order item", showBackground = true, widthDp = 666)
@Composable
fun OrderItemPreview() {
    MotoTabletTheme {
        OrderItem(fakeOrder3, UserType.Mechanic, {}, {}, false)
    }
}

