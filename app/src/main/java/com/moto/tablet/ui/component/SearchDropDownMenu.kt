package com.moto.tablet.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.PopupProperties
import com.moto.tablet.R
import com.moto.tablet.ui.theme.MotoTabletTheme


@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun SearchDropDownMenu(
    modifier: Modifier,
    title: String,
    textFieldValue: String,
    placeHolderText: String,
    list: List<String>,
    onInputChanged: (changed: String) -> Unit,
    onSelectionChange: (selected: String) -> Unit
) {
    Column(
        modifier = modifier
    ) {
        Text(
            modifier = Modifier.padding(start = 4.dp, bottom = 4.dp),
            text = title,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        )
        var expanded = list.isNotEmpty()

        ExposedDropdownMenuBox(
            modifier = Modifier.fillMaxWidth(),
            expanded = expanded,
            onExpandedChange = {
                if (!expanded && list.isEmpty()) {
                    return@ExposedDropdownMenuBox
                }
                expanded = !expanded
            }
        ) {
            var textFieldValueState by remember(key1 = textFieldValue) {
                mutableStateOf(
                    TextFieldValue(
                        text = textFieldValue,
                        selection = TextRange(textFieldValue.length)
                    )
                )
            }

            val focusManager = LocalFocusManager.current
            val keyboardController = LocalSoftwareKeyboardController.current
            val isLastTextField = title.equals(stringResource(id = R.string.complete_address), true)
            val requester = FocusRequester()
            var keyboardCapitalization = KeyboardCapitalization.None
            val keyboardType = when(title) {
                stringResource(id = R.string.email_address).uppercase() -> KeyboardType.Email
                stringResource(id = R.string.birthdate).uppercase() -> KeyboardType.Number
                stringResource(id = R.string.contact_number).uppercase() -> KeyboardType.Phone
                stringResource(id = R.string.full_name).uppercase() -> {
                    keyboardCapitalization = KeyboardCapitalization.Sentences
                    KeyboardType.Text
                }
                else -> {
                    KeyboardType.Text
                }
            }
            OutlinedTextField(
                value = textFieldValueState,
                onValueChange = {
                    textFieldValueState = it
                    onInputChanged(it.text)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(requester)
                    .menuAnchor(),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = Color.White,
                    focusedContainerColor = Color.White,
                    unfocusedBorderColor = Color.LightGray,
                    focusedBorderColor = Color.LightGray,
                ),
                placeholder = {
                    Text(
                        text = placeHolderText,
                        color = Color.LightGray,
                        fontSize = 15.sp,
                    )
                },
                shape = RoundedCornerShape(size = 5.dp),
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    imeAction = if (isLastTextField)
                        ImeAction.Done
                    else
                        ImeAction.Next,
                    keyboardType = keyboardType,
                    capitalization = keyboardCapitalization
                ),
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(FocusDirection.Next) },
                    onDone = { keyboardController?.hide() }
                )
            )
            if (title.equals(stringResource(id = R.string.full_name), ignoreCase = true)) {
                LaunchedEffect(key1 = title, block = {
                    requester.requestFocus()
                })
            }

            DropdownMenu(
                modifier = Modifier.exposedDropdownSize(true),
                expanded = expanded,
                properties = PopupProperties(focusable = false),
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
}

@Preview(heightDp = 100, showBackground = true)
@Composable
fun SearchDropDownMenuPreview() {
    MotoTabletTheme {
        SearchDropDownMenu(
            modifier = Modifier,
            title = "FULL NAME",
            textFieldValue = "",
            placeHolderText = "Enter something",
            list = listOf("a", "b"),
            onInputChanged = {},
            onSelectionChange = {}
        )
    }
}