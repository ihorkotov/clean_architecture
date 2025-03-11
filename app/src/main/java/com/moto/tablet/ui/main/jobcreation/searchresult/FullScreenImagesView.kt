package com.moto.tablet.ui.main.jobcreation.searchresult

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter.Companion.tint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.moto.tablet.ui.theme.BlackTextColor
import com.moto.tablet.ui.theme.MotoTabletTheme

@OptIn(ExperimentalFoundationApi::class, ExperimentalGlideComposeApi::class)
@Composable
fun FullScreenImagesView(
    navController: NavController,
    images: List<String>
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        val pagerState = rememberPagerState(pageCount = {
            images.size
        })

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 78.dp),
        ) {
            Image(
                imageVector = Icons.Default.Close,
                contentDescription = "close",
                contentScale = ContentScale.Inside,
                modifier = Modifier
                    .padding(start = 36.dp)
                    .size(45.dp)
                    .background(
                        color = Color.Gray,
                        shape = CircleShape
                    )
                    .clickable(
                        role = Role.Button
                    ) {
                        navController.navigateUp()
                    },
                colorFilter = tint(color = BlackTextColor)
            )
            Text(
                text = "${pagerState.currentPage + 1}/${images.size}",
                color = Color.LightGray,
                fontSize = 22.sp,
                modifier = Modifier.align(Alignment.Center),
            )
        }

        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 24.dp)
        ) {page ->
            GlideImage(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp),
                model = images[page],
                contentDescription = "part image"
            )
        }
    }
}

@Preview("full image preview", widthDp = 768, heightDp = 1024)
@Composable
fun FullScreenImagesViewPreview() {
    MotoTabletTheme {
        FullScreenImagesView(
            navController = rememberNavController(),
            images = listOf("da")
        )
    }
}
