package com.moto.tablet.ui.main.jobcreation.searchproduct

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.moto.tablet.R
import com.moto.tablet.ui.component.BackButton
import com.moto.tablet.ui.component.DropDownTrailingIcon
import com.moto.tablet.ui.component.ImageBackgroundBox
import com.moto.tablet.ui.component.TitleText
import com.moto.tablet.ui.main.Screen
import com.moto.tablet.ui.theme.CartItemBackgroundColor
import com.moto.tablet.ui.theme.MotoTabletTheme
import com.moto.tablet.ui.theme.Orange
import com.moto.tablet.ui.theme.PlaceHolderColor

@Composable
fun SearchProduct(navController: NavController) {
    ImageBackgroundBox(imageResourceId = R.drawable.bg_pms_detail) {
        val viewModel: SearchProductViewModel = hiltViewModel()
        SearchProductMain(
            navController = navController,
            cartItemCount = viewModel.cartItemCount,
            searchQuery = viewModel.query,
            onQueryChanged = {
                viewModel.updateQuery(it)
            },
            onSearch = {
                viewModel.startSearch()
            },
            selectedBrand = viewModel.selectedBrand,
            selectedModel = viewModel.selectedModel,
            selectedYear = viewModel.selectedYear,
            brandList = viewModel.brandList,
            modelList = viewModel.modelList,
            yearList = viewModel.yearList,
            onSelectionChange = { type, selected ->
                viewModel.updateSelection(type, selected)
            },
            onClickSearch = {
                viewModel.startSearch()
            }
        )
        val goToNextPage = viewModel.goToNextPage
        if (goToNextPage) {
            navController.navigate(Screen.SearchProductResult.route)
            viewModel.goToNextPage = false
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchProductMain(
    navController: NavController,
    cartItemCount: Int,
    searchQuery: String,
    onQueryChanged: (query: String) -> Unit,
    onSearch: () -> Unit,
    selectedBrand: String,
    selectedModel: String,
    selectedYear: String,
    brandList: List<String>,
    modelList: List<String>,
    yearList: List<String>,
    onSelectionChange: (type: DropDownType, selected: String) -> Unit,
    onClickSearch: () -> Unit
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

            Row(
                modifier = Modifier
                    .padding(end = 52.dp)
                    .size(width = 115.dp, height = 45.dp)
                    .align(Alignment.CenterEnd)
                    .border(
                        width = 1.dp,
                        shape = RoundedCornerShape(50),
                        color = Color.White
                    )
                    .clickable(
                        role = Role.Button
                    ) {
                        navController.navigate(Screen.Checkout.route)
                    },
            ) {
                Image(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f)
                        .background(
                            color = CartItemBackgroundColor,
                            shape = RoundedCornerShape(
                                topStartPercent = 50,
                                bottomStartPercent = 50
                            )
                        ),
                    painter = painterResource(id = R.drawable.ic_bag),
                    contentScale = ContentScale.Inside,
                    contentDescription = "bag"
                )
                Box(modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
                    .background(
                        color = Orange,
                        shape = RoundedCornerShape(
                            topEndPercent = 50,
                            bottomEndPercent = 50
                        )
                    )
                ) {
                    Text(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(end = 10.dp),
                        text = cartItemCount.toString(),
                        color = Color.White,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                }
            }
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
                .height(height = 600.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            SearchBar(
                query = searchQuery,
                modifier = Modifier
                    .size(width = 430.dp, height = 134.dp)
                    .padding(top = 73.dp)
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
                onSearch = {
                    onSearch()
                },
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
                        text = stringResource(id = R.string.search_item).uppercase(),
                        color = PlaceHolderColor,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            ) {}

            Text(
                modifier = Modifier.padding(top = 60.dp),
                text = stringResource(id = R.string.search_motor_brand).uppercase(),
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Gray
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .padding(top = 20.dp, bottom = 48.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                SelectDropDownMenu(
                    type = DropDownType.Brand,
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 12.dp),
                    list = brandList,
                    selectedText = selectedBrand,
                    onSelectionChange = {
                        onSelectionChange(DropDownType.Brand, it)
                    }
                )

                SelectDropDownMenu(
                    type = DropDownType.Model,
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 12.dp),
                    list = modelList,
                    selectedText = selectedModel,
                    onSelectionChange = {
                        onSelectionChange(DropDownType.Model, it)
                    }
                )

                SelectDropDownMenu(
                    type = DropDownType.Year,
                    modifier = Modifier.weight(1f),
                    list = yearList,
                    selectedText = selectedYear,
                    onSelectionChange = {
                        onSelectionChange(DropDownType.Year, it)
                    }
                )
            }

            Button(
                modifier = Modifier
                    .size(width = 215.dp, height = 52.dp),
                shape = Shapes().medium,
                onClick = onClickSearch
            ) {
                Text(
                    text = stringResource(id = R.string.search).uppercase(),
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectDropDownMenu(
    type: DropDownType,
    modifier: Modifier,
    list: List<String>,
    selectedText: String,
    onSelectionChange: (selected: String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val placeHolderText = when(type) {
        DropDownType.Brand -> R.string.select_brand
        DropDownType.Model -> R.string.select_model
        DropDownType.Year -> R.string.select_year
    }
    ExposedDropdownMenuBox(
        modifier = modifier
            .background(color = Color.White),
        expanded = expanded,
        onExpandedChange = {
            expanded = !expanded
        }
    ) {
        OutlinedTextField(
            value = selectedText,
            onValueChange = {},
            readOnly = true,
            trailingIcon = { DropDownTrailingIcon(expanded = expanded) },
            modifier = Modifier.menuAnchor(),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White,
                unfocusedBorderColor = Color.Gray,
                focusedBorderColor = Color.Gray,
                unfocusedTrailingIconColor = Color.Gray,
                focusedTrailingIconColor = Color.Gray,
            ),
            placeholder = {
                Text(
                    text = stringResource(id = placeHolderText).uppercase(),
                    color = PlaceHolderColor,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )
            },
            shape = RoundedCornerShape(size = 5.dp)
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            list.forEach { item ->
                DropdownMenuItem(
                    text = { Text(text = item) },
                    onClick = {
                        onSelectionChange(item)
                        expanded = false
                    }
                )
            }
        }
    }
}


@Preview("search product preview", widthDp = 768, heightDp = 1024)
@Composable
fun SearchProductPreview() {
    MotoTabletTheme {
        ImageBackgroundBox(imageResourceId = R.drawable.bg_pms_detail) {
            SearchProductMain(
                navController = rememberNavController(),
                cartItemCount = 4,
                searchQuery = "",
                onQueryChanged = {},
                onSearch = {  },
                selectedBrand = "",
                selectedModel = "",
                selectedYear = "",
                brandList = listOf(),
                modelList = listOf(),
                yearList = listOf(),
                onSelectionChange = { _, _ -> },
                onClickSearch = {}
            )
        }
    }
}