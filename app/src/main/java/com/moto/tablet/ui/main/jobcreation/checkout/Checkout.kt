package com.moto.tablet.ui.main.jobcreation.checkout

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.moto.tablet.R
import com.moto.tablet.model.LaborItem
import com.moto.tablet.model.PMSPackage
import com.moto.tablet.model.PartProduct
import com.moto.tablet.model.fakeCustomer
import com.moto.tablet.model.fakePackage
import com.moto.tablet.model.fakePackages
import com.moto.tablet.model.fakePartProducts
import com.moto.tablet.ui.component.BackButton
import com.moto.tablet.ui.component.CustomerInfo
import com.moto.tablet.ui.component.ImageBackgroundBox
import com.moto.tablet.ui.component.QuantityCount
import com.moto.tablet.ui.component.TitleText
import com.moto.tablet.ui.main.Screen
import com.moto.tablet.ui.main.jobcreation.customerprofile.JobCreatedDialog
import com.moto.tablet.ui.theme.BlackTextColor
import com.moto.tablet.ui.theme.BorderColor
import com.moto.tablet.ui.theme.InterFontFamily
import com.moto.tablet.ui.theme.MotoTabletTheme
import com.moto.tablet.ui.theme.OpenSansFontFamily
import com.moto.tablet.ui.theme.Orange
import com.moto.tablet.ui.theme.TextColor
import com.moto.tablet.util.currencyString
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn

@Composable
fun Checkout(navController: NavController) {
    ImageBackgroundBox(imageResourceId = R.drawable.bg_pms_detail) {
        val checkoutViewModel: CheckoutViewModel = hiltViewModel()
        checkoutViewModel.refreshOrders()
        val cartItemCount by checkoutViewModel.cartTotalCount.collectAsState(0)
        val cartTotalPrice by checkoutViewModel.cartTotalPrice.collectAsState(0f)
        val packages by checkoutViewModel.cartPmsPackages.collectAsState(listOf())
        val parts by checkoutViewModel.cartPartProducts.collectAsState(listOf())
        val laborItems by checkoutViewModel.laborItems.collectAsState(listOf())
        LaunchedEffect(Unit) {
            checkoutViewModel.laborInputPrice.launchIn(this)
        }

        CheckoutMain(
            navController = navController,
            cartItemCount = cartItemCount,
            cartTotalPrice = cartTotalPrice,
            packages = packages,
            parts = parts,
            laborItems = laborItems,
            laborPriceStringState = checkoutViewModel.laborPriceString,
            customerNote = checkoutViewModel.customerNote,
            countForItem = {
                when (it) {
                    is PMSPackage -> checkoutViewModel.packageCount(it)
                    is PartProduct -> checkoutViewModel.partProductCount(it)
                    is LaborItem -> checkoutViewModel.laborItemCount(it)
                    else -> 0
                }
            },
            onClickRemove = {
                checkoutViewModel.removeItem(it)
            },
            increaseCount = {
                checkoutViewModel.increaseItemCount(it)
            },
            decreaseCount = {
                checkoutViewModel.decreaseItemCount(it)
            },
            onLaborItemPriceChanged = { _, changedPriceString ->
                checkoutViewModel.onLaborItemPriceChanged(changedPriceString)
            },
            onCustomerNoteChanged = {
                checkoutViewModel.updateCustomerNote(it)
            },
            orderItemClicked = {
                if (it is PMSPackage) {
                    checkoutViewModel.selectPackageEdit(it)
                    navController.navigate(Screen.PackageEdit.route)
                }
            },
            onClickPlaceOrder = {
                checkoutViewModel.placeOrder()
            },
        )
        val jobOrderNumber = checkoutViewModel.jobCreatedNumber
        if (jobOrderNumber.isNotEmpty()) {
            JobCreatedDialog(jorOrderNumber = jobOrderNumber) {
                checkoutViewModel.jobCreatedNumber = ""
                navController.popBackStack(
                    Screen.JobOrderCreation.route, inclusive = false
                )
            }
        }
    }
}

@Composable
fun CheckoutMain(
    navController: NavController,
    cartItemCount: Int,
    cartTotalPrice: Float,
    packages: List<PMSPackage>,
    parts: List<PartProduct>,
    laborItems: List<LaborItem>,
    laborPriceStringState: StateFlow<String>,
    customerNote: String,
    countForItem: (item: Any?) -> Int,
    onClickRemove: (item: Any?) -> Unit,
    increaseCount: (item: Any?) -> Unit,
    decreaseCount: (item: Any?) -> Unit,
    onLaborItemPriceChanged: (laborItem: LaborItem, changedPriceString: String) -> Unit,
    onCustomerNoteChanged: (note: String) -> Unit,
    orderItemClicked: (item: Any) -> Unit,
    onClickPlaceOrder: () -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize()) {

        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier.fillMaxWidth().padding(top = 62.dp),
            ) {
                BackButton(
                    modifier = Modifier.padding(start = 32.dp).align(Alignment.CenterStart)
                ) {
                    navController.navigateUp()
                }

                TitleText(
                    modifier = Modifier.align(Alignment.Center),
                    title = stringResource(id = R.string.checkout).uppercase(),
                )
            }

            CustomerInfo(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp)
                    .padding(top = 24.dp),
                customer = fakeCustomer
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp)
                    .padding(top = 10.dp)
                    .background(Color.White)
                    .weight(1f),
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp, start = 20.dp, end = 40.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween

                ) {
                    Text(
                        text = stringResource(id = R.string.order_summary).uppercase(),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Gray
                    )

                    TextButton(onClick = {
                        navController.navigate(Screen.SearchProduct.route)
                    } ) {
                        Text(
                            text = stringResource(id = R.string.add_item),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Red
                        )
                    }

                }
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    contentPadding = PaddingValues(
                        top = 20.dp,
                        start = 36.dp,
                        end = 36.dp,
                        bottom = 96.dp
                    )
                ) {
                    items(packages) {
                        OrderItemPackage(
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(min = 138.dp)
                                .padding(vertical = 20.dp),
                            pmsPackage = it,
                            count = countForItem,
                            remove = { pmsPackage ->
                                onClickRemove(pmsPackage) },
                            increaseCount = { pmsPackage ->
                                increaseCount(pmsPackage)
                            },
                            decreaseCount = { pmsPackage ->
                                decreaseCount(pmsPackage)
                            },
                            orderItemClicked = { pmsPackage ->
                                orderItemClicked(pmsPackage)
                            }
                        )
                        Divider()
                    }
                    items(parts) {
                        OrderItemPartProduct(
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(min = 138.dp)
                                .padding(vertical = 20.dp),
                            partProduct = it,
                            count = countForItem,
                            remove = { partProduct ->
                                onClickRemove(partProduct) },
                            increaseCount = { partProduct ->
                                increaseCount(partProduct)
                            },
                            decreaseCount = { partProduct ->
                                decreaseCount(partProduct)
                            },
                            orderItemClicked = { partProduct ->
                                orderItemClicked(partProduct)
                            }
                        )
                        Divider()
                    }
                    items(laborItems) { laborItem ->
                        LaborItemView(
                            laborItem = laborItem,
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(min = 138.dp)
                                .padding(vertical = 20.dp),
                            count = countForItem,
                            remove = {
                                onClickRemove(it)
                            },
                            increaseCount = {
                                increaseCount(it)
                            },
                            decreaseCount = {
                                decreaseCount(it)
                            },
                            onValueChanged = {
                                onLaborItemPriceChanged(laborItem, it)
                            },
                            laborPriceStringState
                        )
                        Divider()
                    }
                    item {
                        CustomerNote(
                            note = customerNote,
                            onCustomerNoteChanged
                        )
                    }
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .background(Color.White)
                .align(Alignment.BottomCenter)
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
                onClick = onClickPlaceOrder,
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(0.25f),
                shape = RectangleShape,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Orange
                )
            ) {
                Text(
                    text = stringResource(id = R.string.place_order).uppercase(),
                    fontFamily = InterFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            }
        }
    }
}

@Composable
fun OrderItemPackage(
    modifier: Modifier,
    pmsPackage: PMSPackage,
    count: (pmsPackage: PMSPackage) -> Int,
    remove: (pmsPackage: PMSPackage) -> Unit,
    increaseCount: (pmsPackage: PMSPackage) -> Unit,
    decreaseCount: (pmsPackage: PMSPackage) -> Unit,
    orderItemClicked: (pmsPackage: PMSPackage) -> Unit,
) {
    OrderItem(
        modifier = modifier,
        imageResource = pmsPackage.image,
        title = pmsPackage.title,
        description = pmsPackage.description,
        price = pmsPackage.price,
        count = count(pmsPackage),
        remove = { remove(pmsPackage) },
        increaseCount = { increaseCount(pmsPackage) },
        decreaseCount = { decreaseCount(pmsPackage) },
        orderItemClicked = { orderItemClicked(pmsPackage) }
    )
}

@Composable
fun OrderItemPartProduct(
    modifier: Modifier,
    partProduct: PartProduct,
    count: (partProduct: PartProduct) -> Int,
    remove: (partProduct: PartProduct) -> Unit,
    increaseCount: (partProduct: PartProduct) -> Unit,
    decreaseCount: (partProduct: PartProduct) -> Unit,
    orderItemClicked: (partProduct: PartProduct) -> Unit,
) {
    OrderItem(
        modifier = modifier,
        imageResource = R.drawable.ic_pms_package,
        title = partProduct.productName,
        description = partProduct.productName,
        price = partProduct.price,
        count = count(partProduct),
        remove = { remove(partProduct) },
        increaseCount = { increaseCount(partProduct) },
        decreaseCount = { decreaseCount(partProduct) },
        orderItemClicked = { orderItemClicked(partProduct) }
    )
}

@Composable
fun OrderItem(
    modifier: Modifier,
    imageResource: Int,
    title: String,
    description: String,
    price: Float,
    count: Int,
    remove: () -> Unit,
    increaseCount: () -> Unit,
    decreaseCount: () -> Unit,
    orderItemClicked: () -> Unit
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            modifier = Modifier
                .size(width = 124.dp, height = 114.dp)
                .clickable(
                    role = Role.Button,
                    onClick = orderItemClicked
                ),
            painter = painterResource(id = imageResource), contentDescription = "package image",
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier
                .padding(start = 20.dp, top = 16.dp, bottom = 10.dp)
                .weight(1f)
        ) {
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = BlackTextColor
            )
            Text(
                modifier = Modifier
                    .padding(top = 8.dp),
                text = description,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.weight(1f))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                Text(
                    modifier = Modifier.widthIn(min = 80.dp),
                    text = currencyString(price),
                    fontFamily = OpenSansFontFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp
                )

                QuantityCount(
                    modifier = Modifier
                        .width(100.dp)
                        .height(40.dp)
                        .padding(end = 8.dp),
                    count = count,
                    decreaseItemCount = decreaseCount,
                    increaseItemCount = increaseCount
                )
            }
        }

        OutlinedButton(
            modifier = Modifier.padding(horizontal = 24.dp),
            shape = RoundedCornerShape(size = 5.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = Orange,
            ),
            border = BorderStroke(Dp(0.5f), Orange),
            onClick = remove
        ) {
            Text(
                text = stringResource(id = R.string.remove),
                fontFamily = OpenSansFontFamily,
                fontWeight = FontWeight.Normal
            )
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun LaborItemView(
    laborItem: LaborItem,
    modifier: Modifier,
    count: (laborItem: LaborItem) -> Int,
    remove: (laborItem: LaborItem) -> Unit,
    increaseCount: (laborItem: LaborItem) -> Unit,
    decreaseCount: (laborItem: LaborItem) -> Unit,
    onValueChanged:(value: String) -> Unit,
    laborPriceStringState: StateFlow<String>
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            modifier = Modifier.size(width = 124.dp, height = 114.dp),
            painter = painterResource(id = laborItem.image), contentDescription = "labor image",
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier
                .padding(start = 20.dp, top = 10.dp)
                .weight(1f)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = laborItem.title,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = BlackTextColor
            )
            val keyboardController = LocalSoftwareKeyboardController.current
            val priceString by laborPriceStringState.collectAsState()
            OutlinedTextField(
                value = priceString,
                textStyle = TextStyle(
                    fontFamily = OpenSansFontFamily,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal
                ),
                modifier = Modifier
                    .widthIn(min = 80.dp)
                    .padding(vertical = 4.dp),
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Decimal,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        keyboardController?.hide()
                    }
                ),
                onValueChange = {
                    onValueChanged(it)
                }
            )

            QuantityCount(
                modifier = Modifier
                    .width(100.dp)
                    .height(40.dp),
                count = count(laborItem),
                decreaseItemCount = { decreaseCount(laborItem) },
                increaseItemCount = { increaseCount(laborItem) }
            )
        }

        OutlinedButton(
            modifier = Modifier.padding(horizontal = 24.dp),
            shape = RoundedCornerShape(size = 5.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = Orange,
            ),
            border = BorderStroke(Dp(0.5f), Orange),
            onClick = { remove(laborItem) }
        ) {
            Text(
                text = stringResource(id = R.string.remove),
                fontFamily = OpenSansFontFamily,
                fontWeight = FontWeight.Normal
            )
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CustomerNote(
    note: String?,
    onCustomerNoteChanged: (note: String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
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

        val keyboardController = LocalSoftwareKeyboardController.current
        OutlinedTextField(
            value = note ?: "",
            textStyle = TextStyle(
                fontFamily = OpenSansFontFamily,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                color = TextColor
            ),
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(horizontal = 24.dp, vertical = 8.dp),
            shape = RoundedCornerShape(10.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = BorderColor,
                unfocusedBorderColor = BorderColor
            ),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    keyboardController?.hide()
                }
            ),
            onValueChange = {
                onCustomerNoteChanged(it)
            }
        )
    }
}

@Preview("checkout preview", widthDp = 768, heightDp = 1024)
@Composable
fun CheckoutPreview() {
    MotoTabletTheme {
        ImageBackgroundBox(imageResourceId = R.drawable.bg_pms_detail) {
            CheckoutMain(
                navController = rememberNavController(),
                cartItemCount = 3,
                cartTotalPrice = 1000000f,
                packages = fakePackages(),
                parts = fakePartProducts().dropLast(5),
                laborItems = listOf(),
                laborPriceStringState = MutableStateFlow(""),
                customerNote = "",
                countForItem = { 3 },
                onClickRemove = { },
                increaseCount = { },
                decreaseCount = { },
                onLaborItemPriceChanged = { _, _ -> },
                onCustomerNoteChanged = { },
                orderItemClicked = { },
                onClickPlaceOrder = { },
            )
        }
    }
}

@Preview("order item preview", widthDp = 768, heightDp = 138,
    backgroundColor = 0xffffff, showBackground = true)
@Composable
fun OrderItemPackagePreview() {
    MotoTabletTheme {
        OrderItemPackage(Modifier, fakePackage(), { 2 }, {}, {}, {}, {})
    }
}

@Preview("labor item preview", widthDp = 768, heightDp = 138,
    backgroundColor = 0xffffff, showBackground = true)
@Composable
fun LaborItemPackagePreview() {
    MotoTabletTheme {
        LaborItemView(
            laborItem = LaborItem(1, 1100f, "Labor", R.drawable.ic_labor),
            modifier = Modifier,
            count = { 1 },
            remove = { },
            increaseCount = { },
            decreaseCount = { },
            onValueChanged = { },
            MutableStateFlow("")
        )
    }
}


@Preview("customer note preview", widthDp = 768, heightDp = 250,
    backgroundColor = 0xffffff, showBackground = true)
@Composable
fun CustomerNotePreview() {
    MotoTabletTheme {
        CustomerNote(
            "Please add some packages for free"
        ) { }
    }
}


