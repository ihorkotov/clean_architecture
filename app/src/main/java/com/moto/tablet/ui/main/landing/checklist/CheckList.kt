package com.moto.tablet.ui.main.landing.checklist

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.moto.tablet.R
import com.moto.tablet.model.Order
import com.moto.tablet.model.OrderStatus
import com.moto.tablet.model.PMSPackage
import com.moto.tablet.model.PartProduct
import com.moto.tablet.model.UserType
import com.moto.tablet.model.fakeOrder
import com.moto.tablet.model.fakePackage
import com.moto.tablet.ui.component.BackButton
import com.moto.tablet.ui.component.CustomerInfo
import com.moto.tablet.ui.component.DateTimeShow
import com.moto.tablet.ui.component.ImageBackgroundBox
import com.moto.tablet.ui.component.LoadingProgressBar
import com.moto.tablet.ui.component.TitleText
import com.moto.tablet.ui.main.Screen
import com.moto.tablet.ui.main.landing.KEY_ORDER_STATUS_UPDATED
import com.moto.tablet.ui.theme.BlackTextColor
import com.moto.tablet.ui.theme.BorderColor
import com.moto.tablet.ui.theme.Green
import com.moto.tablet.ui.theme.MotoTabletTheme
import com.moto.tablet.ui.theme.TextColor

@Composable
fun CheckList(navController: NavController) {
    ImageBackgroundBox(imageResourceId = R.drawable.bg_pms_detail) {
        val viewModel: CheckListViewModel = hiltViewModel()
        val order = viewModel.order ?: return@ImageBackgroundBox
        var showMechanicCodeDialog by remember { mutableStateOf(false) }
        CheckListMain(
            navController = navController,
            order = order,
            viewModel.userType,
            onItemClicked = {
                when(it) {
                    is PMSPackage -> {
                        viewModel.updateSelectedPMSPackage(it)
                        navController.navigate(Screen.PackageDetail.route)
                    }
                    is PartProduct -> {
                        viewModel.updateSelectedPartProduct(it)
                        navController.navigate(Screen.ProductDetail.route)
                    }
                }
            },
            onClickStart = {
                showMechanicCodeDialog = true
            },
            onClickEnd = {
                navController.navigate(Screen.SaveCheckList.route)
            }
        )

        val showProgress = viewModel.showProgress
        if (showProgress) {
            LoadingProgressBar(modifier = Modifier.align(Alignment.Center))
        }

        if (showMechanicCodeDialog) {
            EnterMechanicCodeDialog(
                userMechanicCode = viewModel.mechanicCode,
                onDismissRequest = {
                    showMechanicCodeDialog = false
                }
            ) {
                showMechanicCodeDialog = false
                viewModel.startOrder()
            }
        }

        if (viewModel.updatedOrderStatus) {
            viewModel.updatedOrderStatus = false
            navController.previousBackStackEntry
                ?.savedStateHandle
                ?.set(KEY_ORDER_STATUS_UPDATED, true)
            navController.popBackStack()
        }
    }
}

@Composable
fun CheckListMain(
    navController: NavController,
    order: Order,
    userType: UserType,
    onItemClicked: (item: Any?) -> Unit,
    onClickStart: () -> Unit,
    onClickEnd: () -> Unit,
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
                    text = stringResource(id = R.string.order_summary).uppercase(),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Gray
                )
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    contentPadding = PaddingValues(
                        start = 36.dp,
                        end = 36.dp,
                        bottom = 20.dp
                    )
                ) {
                    val packages = order.packages ?: emptyList()
                    items(packages) {
                        CheckListItem(it, onItemClicked)
                        Divider()
                    }
                    val parts = order.partProduct ?: emptyList()
                    items(parts) {
                        CheckListItem(it, onItemClicked)
                        Divider()
                    }
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 56.dp)
                                .height(250.dp)
                        ) {
                            Text(
                                modifier = Modifier
                                    .padding(top = 8.dp),
                                text = stringResource(id = R.string.customer_note).uppercase(),
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                                color = Color.Gray
                            )
                            OutlinedTextField(
                                value = order.customerNote ?: "",
                                textStyle = TextStyle(
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Normal,
                                    color = TextColor,
                                ),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .fillMaxHeight()
                                    .padding(vertical = 8.dp)
                                    .padding(start = 8.dp, end = 40.dp),
                                shape = RoundedCornerShape(10.dp),
                                readOnly = true,
                                onValueChange = {},
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = BorderColor,
                                    unfocusedBorderColor = BorderColor
                                )
                            )
                        }
                    }
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
                        containerColor = Green,
                        disabledContainerColor = BorderColor,
                        disabledContentColor = Color.Gray
                    ),
                    enabled = order.status == OrderStatus.NextToService,
                    onClick = onClickStart,
                ) {
                    Text(
                        text = stringResource(id = R.string.start).uppercase()
                    )
                }
                if (userType != UserType.Mechanic) {
                    Button(
                        modifier = Modifier
                            .padding(start = 24.dp)
                            .size(width = 185.dp, height = 55.dp),
                        shape = RoundedCornerShape(5.dp),
                        colors = ButtonDefaults.buttonColors(
                            contentColor = Color.White,
                            disabledContainerColor = BorderColor,
                            disabledContentColor = Color.Gray
                        ),
                        enabled = order.status == OrderStatus.Ongoing,
                        onClick = onClickEnd,
                    ) {
                        Text(
                            text = stringResource(id = R.string.end).uppercase(),
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CheckListItem(
    item: Any?,
    onClickItem: (item: Any?) -> Unit
) {
    val title = when(item) {
        is PMSPackage -> item.title
        is PartProduct -> item.productName
        else -> ""
    }
    Row(
        modifier = Modifier
            .padding(vertical = 16.dp)
            .fillMaxWidth()
            .padding(start = 8.dp, end = 16.dp)
            .clickable(
                role = Role.Button
            ) {
                onClickItem(item)
            },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            fontSize = 16.sp,
            color = BlackTextColor,
        )
        Image(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_arrow_right),
            contentDescription = "Click item"
        )
    }
}

@Preview("checklist preview", widthDp = 768, heightDp = 1024)
@Composable
fun CheckListPreview() {
    MotoTabletTheme {
        ImageBackgroundBox(imageResourceId = R.drawable.bg_pms_detail) {
            CheckListMain(
                navController = rememberNavController(),
                order = fakeOrder,
                userType = UserType.ServiceAdvisor,
                onItemClicked = {},
                onClickStart = {},
                onClickEnd = {},
            )
        }
    }
}

@Preview("check list item preview", showBackground = true)
@Composable
fun CheckListItemPreview() {
    MotoTabletTheme {
        CheckListItem(item = fakePackage()) {}
    }
}