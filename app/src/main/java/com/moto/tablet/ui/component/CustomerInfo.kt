package com.moto.tablet.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.moto.tablet.R
import com.moto.tablet.model.Customer
import com.moto.tablet.model.fakeCustomer
import com.moto.tablet.ui.theme.BlackTextColor
import com.moto.tablet.ui.theme.MotoTabletTheme

@Composable
fun CustomerInfo(
    modifier: Modifier,
    customer: Customer,
) {
    Column(
        modifier = modifier
            .background(Color.White)
            .padding(horizontal = 20.dp)
            .padding(top = 8.dp, bottom = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = stringResource(id = R.string.customer_details).uppercase(),
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Gray
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = stringResource(id = R.string.full_name),
                    fontWeight = FontWeight.Normal,
                    fontSize = 10.sp,
                    color = BlackTextColor
                )
                Text(
                    text = customer.fullName ?: "",
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    color = BlackTextColor
                )
            }

            Column {
                Text(
                    text = stringResource(id = R.string.email_address),
                    fontWeight = FontWeight.Normal,
                    fontSize = 10.sp,
                    color = BlackTextColor
                )
                Text(
                    text = customer.emailAddress ?: "",
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    color = BlackTextColor
                )
            }

            Column {
                Text(
                    text = stringResource(id = R.string.contact_number),
                    fontWeight = FontWeight.Normal,
                    fontSize = 10.sp,
                    color = BlackTextColor
                )
                Text(
                    text = customer.contactNumber?: "",
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    color = BlackTextColor
                )
            }

            Column {
                Text(
                    text = stringResource(id = R.string.birthday),
                    fontWeight = FontWeight.Normal,
                    fontSize = 10.sp,
                    color = BlackTextColor
                )
                Text(
                    text = customer.birthday ?: "",
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    color = BlackTextColor
                )
            }
        }

        Column {
            Text(
                text = stringResource(id = R.string.complete_address),
                fontWeight = FontWeight.Normal,
                fontSize = 10.sp,
                color = BlackTextColor
            )
            Text(
                text = customer.completeAddress ?: "",
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp,
                color = BlackTextColor
            )
        }
    }
}

@Preview("customer info preview", widthDp = 710)
@Composable
fun CustomerInfoPreview() {
    MotoTabletTheme {
        CustomerInfo(modifier = Modifier.fillMaxWidth(), customer = fakeCustomer)
    }
}