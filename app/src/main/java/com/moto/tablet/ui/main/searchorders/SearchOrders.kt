package com.moto.tablet.ui.main.searchorders

import android.app.Activity
import android.content.Intent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.moto.tablet.R
import com.moto.tablet.model.Order
import com.moto.tablet.model.UserType
import com.moto.tablet.model.fakeOrders
import com.moto.tablet.ui.component.ImageBackgroundBox
import com.moto.tablet.ui.component.LoadingProgressBar
import com.moto.tablet.ui.component.TitleText
import com.moto.tablet.ui.login.LoginActivity
import com.moto.tablet.ui.main.Screen
import com.moto.tablet.ui.main.landing.KEY_ORDER_STATUS_UPDATED
import com.moto.tablet.ui.main.landing.OrderHeaderItem
import com.moto.tablet.ui.main.landing.OrderItem
import com.moto.tablet.ui.theme.MotoTabletTheme
import com.moto.tablet.ui.theme.NavButtonColor
import com.moto.tablet.ui.theme.PlaceHolderColor

@Composable
fun SearchOrders(navController: NavController) {
    ImageBackgroundBox(imageResourceId = R.drawable.bg_landing) {
        val viewModel: SearchOrdersViewModel = hiltViewModel()
        val query by viewModel.query.collectAsState(initial = "")
        var showLogoutDialog by remember {
            mutableStateOf(false)
        }
        SearchOrdersMain(
            searchQuery = query,
            orders = viewModel.orders,
            userType = viewModel.userType,
            onQueryChanged = {
                viewModel.updateQuery(it)
            },
            onClickView = {
                viewModel.updateSelectedCheckList(it)
                navController.navigate(Screen.CheckList.route)
            },
            onClickLogout = {
                showLogoutDialog = true
            }
        )

        val updatedOrderStatus = navController.currentBackStackEntry?.savedStateHandle?.getLiveData<Boolean>(
            KEY_ORDER_STATUS_UPDATED
        )?.observeAsState()
        updatedOrderStatus?.value?.let {
            if (it) {
                navController.currentBackStackEntry?.savedStateHandle?.set(KEY_ORDER_STATUS_UPDATED, false)
                viewModel.refreshOrders()
            }
        }

        val showProcess = viewModel.showProgress
        if (showProcess) {
            LoadingProgressBar(modifier = Modifier.align(Alignment.Center))
        }

        val activity = LocalContext.current as? Activity
        if (showLogoutDialog) {
            LogoutAlertDialog(
                onConfirmation = {
                    viewModel.logout()
                    activity?.startActivity(Intent(activity, LoginActivity::class.java))
                    activity?.finish()
                    showLogoutDialog = false
                },
                onDismissRequest = {
                    showLogoutDialog = false
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun SearchOrdersMain(
    searchQuery: String,
    orders: List<Order>,
    userType: UserType,
    onQueryChanged: (query: String) -> Unit,
    onClickView: (order: Order) -> Unit,
    onClickLogout: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(),
        ) {
            TitleText(
                modifier = Modifier
                    .padding(top = 62.dp)
                    .align(Alignment.Center),
                title = stringResource(id = R.string.job_orders).uppercase(),
            )

            OutlinedButton(
                modifier = Modifier
                    .padding(top = 36.dp, end = 32.dp)
                    .size(width = 126.dp, height = 50.dp)
                    .border(width = 2.dp, color = Color.White, shape = RoundedCornerShape(50))
                    .align(Alignment.TopEnd),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = NavButtonColor,
                    contentColor = Color.White
                ),
                onClick = onClickLogout
            ) {
                Text(text = stringResource(id = R.string.logout).uppercase())
            }
        }

        Column(
            modifier = Modifier
                .padding(horizontal = 40.dp)
                .padding(top = 40.dp, bottom = 130.dp)
                .fillMaxSize()
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(10.dp)
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SearchBar(
                query = searchQuery,
                modifier = Modifier
                    .size(width = 430.dp, height = 111.dp)
                    .padding(top = 50.dp)
                    .shadow(
                        elevation = 6.dp,
                        shape = RoundedCornerShape(size = 10.dp),
                        ambientColor = Color.Gray
                    )
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(size = 10.dp)
                    ),
                onQueryChange = onQueryChanged,
                onSearch = {},
                active = false,
                onActiveChange = {},
                colors = SearchBarDefaults.colors(
                    containerColor = Color.Transparent
                ),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "",
                        tint = PlaceHolderColor
                    )
                },
                placeholder = {
                    Text(
                        text = stringResource(id = R.string.search_jo_number_customer_name).uppercase(),
                        color = PlaceHolderColor,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                },
                content = {}
            )

            LazyColumn(
                modifier = Modifier
                    .padding(top = 40.dp)
                    .fillMaxSize(),
            ) {
                stickyHeader {
                    OrderHeaderItem(isSearch = true)
                }
                items(orders) {
                    OrderItem(
                        order = it,
                        userType = userType,
                        onClickView = onClickView,
                        onClickRemove = {},
                        isSearch = true,
                    )
                    Divider(
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun LogoutAlertDialog(
    onConfirmation: () -> Unit,
    onDismissRequest: () -> Unit
) {
    AlertDialog(
        title = {
            Text(text = stringResource(id = R.string.logout))
        },
        text = {
            Text(text = stringResource(id = R.string.logout_alert_dialog_message))
        },
        onDismissRequest = { },
        confirmButton = {
            TextButton(
                onClick = onConfirmation
            ) {
                Text(stringResource(id = R.string.yes))
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismissRequest
            ) {
                Text(stringResource(id = R.string.cancel))
            }
        },
    )
}


@Preview("landing screen", widthDp = 768, heightDp = 1024)
@Composable
fun LandingPreview() {
    MotoTabletTheme {
        ImageBackgroundBox(imageResourceId = R.drawable.bg_landing) {
            SearchOrdersMain(
                "",
                orders = fakeOrders,
                userType = UserType.ServiceAdvisor,
                {},
                {},
                {},
            )
        }
    }
}

