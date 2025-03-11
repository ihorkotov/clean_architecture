package com.moto.tablet.ui.main.jobcreation.packageedit

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
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
import com.moto.tablet.model.PMSPackage
import com.moto.tablet.model.PMSPackageContent
import com.moto.tablet.model.fakePackage
import com.moto.tablet.ui.component.BackButton
import com.moto.tablet.ui.component.ImageBackgroundBox
import com.moto.tablet.ui.component.QuantityCount
import com.moto.tablet.ui.theme.BorderColor
import com.moto.tablet.ui.theme.Green
import com.moto.tablet.ui.theme.MotoTabletTheme
import com.moto.tablet.ui.theme.OpenSansFontFamily
import com.moto.tablet.util.currencyString

@Composable
fun PackageEdit(navController: NavController) {
    ImageBackgroundBox(imageResourceId = R.drawable.bg_pms_detail) {
        val viewModel: PackageEditViewModel = hiltViewModel()
        val pmsPackage by viewModel.selectedPackage.collectAsState()
        var showDialog by remember { mutableStateOf(false) }
        PackageEditMain(
            navController = navController,
            pmsPackage = pmsPackage,
            countInCart = viewModel.getPackageCount(pmsPackage),
            update = { count ->
                viewModel.updatePackageCount(pmsPackage, count)
                showDialog = true
            },
            deleteContent = {
                viewModel.deleteContentFromPackage(pmsPackage, it)
            }
        )
        if (showDialog) {
            ShowUpdateDialog {
                showDialog = false
                navController.navigateUp()
            }
        }
    }
}

@Composable
fun PackageEditMain(
    navController: NavController,
    pmsPackage: PMSPackage,
    countInCart: Int,
    update: (count: Int) -> Unit,
    deleteContent: (content: PMSPackageContent) -> Unit,
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
                        text = pmsPackage.availableItemsCountString(LocalContext.current.resources),
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

                    var count by remember { mutableIntStateOf(countInCart) }
                    QuantityCount(
                        modifier = Modifier
                            .height(55.dp)
                            .fillMaxWidth(),
                        count = count,
                        decreaseItemCount = { count = maxOf(0, count - 1) } ,
                        increaseItemCount = { count += 1 },
                    )
                    var showCheckOut by remember { mutableStateOf(false) }

                    Button(
                        onClick = {
                            if (count > 0) {
                                update(count)
                                showCheckOut = true
                            } },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Green
                        ),
                        modifier = Modifier
                            .padding(top = 20.dp)
                            .fillMaxWidth(),
                        shape = RoundedCornerShape(4.dp),
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Text(
                            text = stringResource(id = R.string.update).uppercase(),
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
                    items(pmsPackage.contentList.filter { !it.isRemoved }) { content ->
                        RemovablePackageContentItem(
                            modifier = Modifier
                                .fillMaxWidth(),
                            content = content,
                            deleteContent = { deleteContent(it) }
                        )
                        Divider(
                            modifier = Modifier.padding(horizontal = 12.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun RemovablePackageContentItem(
    modifier: Modifier,
    content: PMSPackageContent,
    deleteContent: (content: PMSPackageContent) -> Unit
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier
                .padding(start = 16.dp)
                .width(32.dp)
                .aspectRatio(1f)
                .clickable(
                    role = Role.Button,
                    onClick = { deleteContent(content) }
                ),
            painter = painterResource(id = R.drawable.ic_delete),
            contentDescription = "",
            contentScale = ContentScale.None
        )

        Column(
            modifier = Modifier
                .padding(start = 16.dp)
                .padding(vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = content.title,
                fontFamily = OpenSansFontFamily,
                fontSize = 16.sp
            )
            Text(
                text = content.title,
                fontFamily = OpenSansFontFamily,
                fontSize = 12.sp,
                color = Color.LightGray
            )
        }

    }
}

@Composable
fun ShowUpdateDialog(
    onConfirmation: () -> Unit,
) {
    AlertDialog(
        text = {
            Text(text = stringResource(id = R.string.cart_updated))
        },
        onDismissRequest = {
            onConfirmation()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmation()
                }
            ) {
                Text(stringResource(id = R.string.ok))
            }
        },
    )
}

@Preview("pms detail", widthDp = 768, heightDp = 1024)
@Composable
fun PackageEditPreview() {
    MotoTabletTheme {
        ImageBackgroundBox(imageResourceId = R.drawable.bg_pms_detail) {
            PackageEditMain(
                rememberNavController(),
                fakePackage(),
                1,
                { },
                { },
            )
        }
    }
}

@Preview("Package content item", widthDp = 200, heightDp = 62,
    backgroundColor = 0xFFFFFFFF, showBackground = true)
@Composable
fun PackageContentItemPreview() {
    MotoTabletTheme {
        RemovablePackageContentItem(
            Modifier,
            PMSPackageContent(1,"Change Oil", "Change Oil description"),
        ) { }
    }
}
