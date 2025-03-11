package com.moto.tablet.ui.main.jobcreation.customerprofile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import com.moto.tablet.ui.component.BackButton
import com.moto.tablet.ui.component.ImageBackgroundBox
import com.moto.tablet.ui.component.SearchDropDownMenu
import com.moto.tablet.ui.component.TitleText
import com.moto.tablet.ui.main.Screen
import com.moto.tablet.ui.theme.MotoTabletTheme
import com.moto.tablet.ui.theme.OpenSansFontFamily
import com.moto.tablet.ui.theme.TextColor
import com.moto.tablet.util.PRIVACY_POLICY_URL
import com.moto.tablet.util.PRIVACY_POLICY_URL_TAG
import com.moto.tablet.util.SnackbarManager
import com.moto.tablet.util.TERMS_AND_CONDITIONS_URL
import com.moto.tablet.util.TERMS_AND_CONDITIONS_URL_TAG

@Composable
fun CustomerProfile(navController: NavController) {
    ImageBackgroundBox(imageResourceId = R.drawable.bg_pms_detail) {
        val viewModel: CustomerProfileViewModel = hiltViewModel()
        val searchedCustomerNames = viewModel.searchCustomerNames.collectAsState(listOf())
        CustomerProfileMain(
            navController = navController,
            name = viewModel.name,
            searchedNames = searchedCustomerNames.value,
            birthdate = viewModel.birthdate,
            email = viewModel.email,
            contactNumber = viewModel.contactNumber,
            completeAddress = viewModel.completeAddress,
            termsAndConditionsChecked = viewModel.termsAndConditionsChecked,
            privacyPolicyChecked = viewModel.privacyPolicyChecked,
            receiveMarketingChecked = viewModel.receiveMarketingChecked,
            onValueChanged = { type, name ->
                viewModel.updateInfo(type, name)
            },
            onSelection = { type, selected ->
                viewModel.updateSelection(type, selected)
            },
            onTermsCheckedChanged = {
                viewModel.termsAndConditionsCheckedChanged(it)
            },
            onPrivacyPolicyCheckedChanged = {
                viewModel.privacyPolicyCheckedChanged(it)
            },
            onReceiveMarketingCheckedChanged = {
                viewModel.receiveMarketingCheckedChanged(it)
            },
            onClickSave = {
                viewModel.onClickSave()
            }
        )
        val goToNextPage = viewModel.goToNextPage
        if (goToNextPage) {
            navController.navigate(Screen.Checkout.route)
            viewModel.goToNextPage = false
        }
    }
}

@Composable
fun CustomerProfileMain(
    navController: NavController,
    name: String,
    searchedNames: List<String>,
    birthdate: String,
    email: String,
    contactNumber: String,
    completeAddress: String,
    termsAndConditionsChecked: Boolean,
    privacyPolicyChecked: Boolean,
    receiveMarketingChecked: Boolean,
    onValueChanged: (type: CustomerInfoType, value: String) -> Unit,
    onSelection: (type: CustomerInfoType, selected: String) -> Unit,
    onTermsCheckedChanged: (isChecked: Boolean) -> Unit,
    onPrivacyPolicyCheckedChanged: (isChecked: Boolean) -> Unit,
    onReceiveMarketingCheckedChanged: (isChecked: Boolean) -> Unit,
    onClickSave: () -> Unit
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
                title = stringResource(id = R.string.customer_profile).uppercase(),
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
                .height(height = 660.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 36.dp)
                    .padding(horizontal = 32.dp)
            ) {
                SearchDropDownMenu(
                    modifier = Modifier
                        .weight(0.6f)
                        .padding(end = 16.dp),
                    title = stringResource(id = R.string.full_name).uppercase(),
                    textFieldValue = name,
                    placeHolderText = stringResource(id = R.string.enter_first_name_last_name),
                    list = searchedNames,
                    onInputChanged = {
                        onValueChanged(CustomerInfoType.Name, it)
                    },
                    onSelectionChange = {
                        onSelection(CustomerInfoType.Name, it)
                    }
                )

                SearchDropDownMenu(
                    modifier = Modifier.weight(0.35f),
                    title = stringResource(id = R.string.birthdate).uppercase(),
                    textFieldValue = birthdate,
                    placeHolderText = stringResource(id = R.string.enter_birthdate),
                    list = listOf(),
                    onInputChanged = {
                        onValueChanged(CustomerInfoType.Birthdate, it)
                    },
                    onSelectionChange = {}
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp)
                    .padding(horizontal = 32.dp)
            ) {
                SearchDropDownMenu(
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 16.dp),
                    title = stringResource(id = R.string.email_address).uppercase(),
                    textFieldValue = email,
                    placeHolderText = stringResource(id = R.string.enter_email_address),
                    list = listOf(),
                    onInputChanged = {
                        onValueChanged(CustomerInfoType.EmailAddress, it)
                    },
                    onSelectionChange = {}
                )

                SearchDropDownMenu(
                    modifier = Modifier.weight(1f),
                    title = stringResource(id = R.string.contact_number).uppercase(),
                    textFieldValue = contactNumber,
                    placeHolderText = stringResource(id = R.string.enter_contact_number),
                    list = listOf(),
                    onInputChanged = {
                        onValueChanged(CustomerInfoType.ContactNumber, it)
                    },
                    onSelectionChange = {}
                )
            }

            SearchDropDownMenu(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp)
                    .padding(horizontal = 32.dp),
                title = stringResource(id = R.string.complete_address).uppercase(),
                textFieldValue = completeAddress,
                placeHolderText = stringResource(id = R.string.enter_complete_address),
                list = listOf(),
                onInputChanged = {
                    onValueChanged(CustomerInfoType.CompleteAddress, it)
                },
                onSelectionChange = {}
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 36.dp)
                    .padding(horizontal = 36.dp)
                    .height(26.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = termsAndConditionsChecked,
                    onCheckedChange = onTermsCheckedChanged
                )

                val annotatedText = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            fontSize = 14.sp,
                            fontFamily = OpenSansFontFamily,
                            fontWeight = FontWeight.Normal,
                            color = TextColor
                        )
                    ) {
                        append(stringResource(id = R.string.i_agree_to_the))
                        append(" ")
                    }

                    pushStringAnnotation(
                        tag = TERMS_AND_CONDITIONS_URL_TAG, annotation = TERMS_AND_CONDITIONS_URL
                    )
                    withStyle(
                        style = SpanStyle(
                            fontSize = 14.sp,
                            fontFamily = OpenSansFontFamily,
                            fontWeight = FontWeight.Normal,
                            color = Color.Red
                        )
                    ) {
                        append(stringResource(id = R.string.terms_and_conditions))
                    }
                    pop()
                }
                ClickableText(
                    text = annotatedText,
                    onClick = { offset ->
                        annotatedText.getStringAnnotations(
                            tag = TERMS_AND_CONDITIONS_URL_TAG, start = offset, end = offset
                        ).firstOrNull()?.let { annotation ->
                            SnackbarManager.showMessage("Clicked URL ${annotation.item}")
                        }
                    }
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 36.dp)
                    .padding(top = 6.dp)
                    .height(26.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = privacyPolicyChecked,
                    onCheckedChange = onPrivacyPolicyCheckedChanged
                )

                val annotatedText = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            fontSize = 14.sp,
                            fontFamily = OpenSansFontFamily,
                            fontWeight = FontWeight.Normal,
                            color = TextColor
                        )
                    ) {
                        append(stringResource(id = R.string.i_agree_to_the))
                        append(" ")
                    }

                    pushStringAnnotation(
                        tag = PRIVACY_POLICY_URL_TAG, annotation = PRIVACY_POLICY_URL
                    )
                    withStyle(
                        style = SpanStyle(
                            fontSize = 14.sp,
                            fontFamily = OpenSansFontFamily,
                            fontWeight = FontWeight.Normal,
                            color = Color.Red
                        )
                    ) {
                        append(stringResource(id = R.string.privacy_policy))
                    }
                    pop()
                }
                ClickableText(
                    text = annotatedText,
                    onClick = { offset ->
                        annotatedText.getStringAnnotations(
                            tag = PRIVACY_POLICY_URL_TAG, start = offset, end = offset
                        ).firstOrNull()?.let { annotation ->
                            SnackbarManager.showMessage("Clicked URL ${annotation.item}")
                        }
                    }
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 36.dp)
                    .padding(top = 6.dp)
                    .height(26.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = receiveMarketingChecked,
                    onCheckedChange = onReceiveMarketingCheckedChanged
                )
                Text(
                    text = stringResource(id = R.string.want_to_receive_market_promotions),
                    fontSize = 14.sp
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                modifier = Modifier
                    .padding(bottom = 40.dp)
                    .size(width = 274.dp, height = 52.dp),
                shape = Shapes().medium,
                onClick = onClickSave
            ) {
                Text(
                    text = stringResource(id = R.string.save).uppercase(),
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }
    }
}

@Preview("search product preview", widthDp = 768, heightDp = 1024)
@Composable
fun CustomerProfilePreview() {
    MotoTabletTheme {
        ImageBackgroundBox(imageResourceId = R.drawable.bg_pms_detail) {
            CustomerProfileMain(
                navController = rememberNavController(),
                name = "",
                searchedNames = listOf(),
                birthdate = "",
                email = "",
                contactNumber = "",
                completeAddress = "",
                termsAndConditionsChecked = true,
                privacyPolicyChecked = false,
                receiveMarketingChecked = true,
                onValueChanged = {_, _ -> },
                onSelection = {_, _ -> },
                onTermsCheckedChanged = {},
                onPrivacyPolicyCheckedChanged = {},
                onReceiveMarketingCheckedChanged = {},
                onClickSave = {}
            )
        }
    }
}