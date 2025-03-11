package com.moto.tablet.ui.main.jobcreation.customerprofile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.moto.tablet.R
import com.moto.tablet.ui.theme.CountButtonColor
import com.moto.tablet.ui.theme.LightGrayColor
import com.moto.tablet.ui.theme.MotoTabletTheme

@Composable
fun JobCreatedDialog(
    jorOrderNumber: String,
    onDismissRequest: () -> Unit,
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false
        )
    ) {
        Box(
            modifier = Modifier
                .background(Color.White)
                .size(500.dp)
        ) {
            Column(
                modifier = Modifier.align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_job_created),
                    contentDescription = "job created"
                )
                Text(
                    modifier = Modifier.padding(top = 8.dp),
                    text = stringResource(id = R.string.completed).uppercase(),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 25.sp,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    modifier = Modifier.padding(top = 20.dp),
                    text = stringResource(id = R.string.job_order_sharp).uppercase(),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp,
                )
                Text(
                    text = jorOrderNumber,
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .background(CountButtonColor)
                        .border(width = 0.5.dp, color = LightGrayColor)
                        .padding(vertical = 8.dp, horizontal = 40.dp),
                    fontSize = 25.sp,
                    fontWeight = FontWeight.SemiBold
                )

                Button(
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .width(130.dp),
                    onClick = onDismissRequest,
                ) {
                    Text(text = stringResource(id = R.string.ok))
                }
            }
        }
    }
}

@Preview("JobCreatedDialog preview", widthDp = 400, heightDp = 400)
@Composable
fun JobCreationDialogPreview() {
    MotoTabletTheme {
        JobCreatedDialog(
            jorOrderNumber = "qewfadf"
        ) { }
    }
}