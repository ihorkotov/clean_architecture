package com.moto.tablet.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalRippleConfiguration
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.moto.tablet.R
import com.moto.tablet.data.GlobalRepository
import com.moto.tablet.data.UserRepository
import com.moto.tablet.model.PMS
import com.moto.tablet.model.UserType
import com.moto.tablet.ui.main.jobcreation.JobOrderCreation
import com.moto.tablet.ui.main.jobcreation.PMS
import com.moto.tablet.ui.main.jobcreation.checkout.Checkout
import com.moto.tablet.ui.main.jobcreation.customerprofile.CustomerProfile
import com.moto.tablet.ui.main.jobcreation.packagedetail.PackageDetail
import com.moto.tablet.ui.main.jobcreation.packageedit.PackageEdit
import com.moto.tablet.ui.main.jobcreation.pmsdetail.PARAM_PMS_DETAIL_TYPE
import com.moto.tablet.ui.main.jobcreation.pmsdetail.PMSDetail
import com.moto.tablet.ui.main.jobcreation.searchproduct.SearchProduct
import com.moto.tablet.ui.main.jobcreation.searchresult.FullScreenImagesView
import com.moto.tablet.ui.main.jobcreation.searchresult.PartProductDetail
import com.moto.tablet.ui.main.jobcreation.searchresult.SearchProductResult
import com.moto.tablet.ui.main.landing.Landing
import com.moto.tablet.ui.main.landing.checklist.CheckList
import com.moto.tablet.ui.main.landing.savechecklist.SaveCheckList
import com.moto.tablet.ui.main.searchorders.SearchOrders
import com.moto.tablet.ui.theme.MotoTabletTheme
import com.moto.tablet.ui.theme.NavButtonColor
import com.moto.tablet.ui.theme.NavButtonSelectedBackgroundColor
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var globalRepository: GlobalRepository

    @Inject
    lateinit var userRepository: UserRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MotoTabletTheme {
                MainView(
                    globalRepository = globalRepository,
                    userRepository = userRepository,
                )
            }
        }
    }
}

@Composable
fun MainView(
    globalRepository: GlobalRepository,
    userRepository: UserRepository,
) {
    val navController = rememberNavController()
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        NavHost(
            navController,
            startDestination = Screen.Landing.route,
            modifier = Modifier
                .fillMaxSize()
        ) {
            composable(Screen.JobOrderCreation.route) {
                JobOrderCreation(navController)
            }
            composable(Screen.Landing.route) { Landing(navController) }
            composable(Screen.SearchOrders.route) { SearchOrders(navController) }

            composable(Screen.PMS.route) { PMS(navController = navController) }
            composable("${Screen.PMSDetail.route}/{$PARAM_PMS_DETAIL_TYPE}",
                arguments = listOf(navArgument(PARAM_PMS_DETAIL_TYPE) {
                    type = NavType.StringType
                })
            ) { backStackEntry ->
                val type = backStackEntry.arguments?.getString(PARAM_PMS_DETAIL_TYPE) ?: PMS.Scooter.name
                PMS.valueOf(type)
                PMSDetail(pmsType = PMS.valueOf(type), navController = navController)
            }
            composable(Screen.PackageDetail.route) { PackageDetail(navController) }
            composable(Screen.Checkout.route) { Checkout(navController) }
            composable(Screen.PackageEdit.route) { PackageEdit(navController) }
            composable(Screen.SearchProduct.route) { SearchProduct(navController) }
            composable(Screen.SearchProductResult.route) { SearchProductResult(navController) }
            composable(Screen.FullScreenImages.route) { FullScreenImagesView(navController, globalRepository.fullScreenImages) }
            composable(Screen.ProductDetail.route) { PartProductDetail(navController, globalRepository.productForDetail) }
            composable(Screen.Profile.route) { CustomerProfile(navController) }
            composable(Screen.CheckList.route) { CheckList(navController) }
            composable(Screen.SaveCheckList.route) { SaveCheckList(navController) }
        }

        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        val bottomTabItems = if (userRepository.userType() == UserType.Mechanic) {
            listOf(
                Screen.Landing,
                Screen.SearchOrders,
            )
        } else {
            listOf(
                Screen.JobOrderCreation,
                Screen.Landing,
                Screen.SearchOrders,
            )
        }
        val showNavigationBar = bottomTabItems.any { screen ->
            currentDestination?.hierarchy?.any {
                screen.route == it.route
            } == true
        }
        if (!showNavigationBar) {
            return
        }
        BottomNavigationView(
            navController = navController,
            userType = userRepository.userType(),
            globalRepository = globalRepository
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomNavigationView(
    navController: NavController,
    userType: UserType,
    globalRepository: GlobalRepository
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val bottomTabItems = if (userType == UserType.Mechanic) {
        listOf(
            Screen.Landing,
            Screen.SearchOrders,
        )
    } else {
        listOf(
            Screen.JobOrderCreation,
            Screen.Landing,
            Screen.SearchOrders,
        )
    }

    CompositionLocalProvider(LocalRippleConfiguration provides null) {
        NavigationBar(
            modifier = Modifier
                .padding(bottom = 38.dp)
                .requiredSize(width = 396.dp, height = 62.dp)
                .clip(RoundedCornerShape(percent = 50))
                .border(2.dp, Color.White, shape = RoundedCornerShape(50)),
            containerColor = NavButtonColor,
            contentColor = Color.White
        ) {
            bottomTabItems.forEach { screen ->
                val selected = currentDestination?.hierarchy?.any {
                    it.route == screen.route
                } == true
                if (selected) {
                    globalRepository.currentSelectedTab = screen
                }
                var iconSizePadding = 0.dp
                val iconPadding: PaddingValues
                val icon = when (screen) {
                    Screen.JobOrderCreation -> {
                        iconPadding = PaddingValues(start = 36.dp)
                        R.drawable.ic_home_outline_24px
                    }
                    Screen.Landing -> {
                        iconPadding = if (userType == UserType.Mechanic) {
                            PaddingValues(start = 36.dp)
                        } else {
                            PaddingValues(horizontal = 10.dp)
                        }
                        iconSizePadding = 6.dp
                        R.drawable.ic_category
                    }
                    Screen.SearchOrders -> {
                        iconPadding = PaddingValues(end = 36.dp)
                        iconSizePadding = 3.dp
                        R.drawable.ic_list
                    }
                    else -> {
                        return@forEach
                    }
                }
                NavigationBarItem(
                    icon = {
                        Icon(
                            painter = painterResource(id = icon),
                            tint = Color.White,
                            contentDescription = null,
                            modifier = Modifier
                                .size(40.dp)
                                .padding(iconSizePadding)
                                .background(Color.Transparent)
                        )
                    },
                    selected = selected,
                    onClick = {
                        navController.navigate(screen.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            // Avoid multiple copies of the same destination when
                            // re-selecting the same item
                            launchSingleTop = true
                            // Restore state when re-selecting a previously selected item
                            restoreState = true
                        }
                    },
                    colors = NavigationBarItemDefaults.colors(
                        indicatorColor = NavButtonSelectedBackgroundColor
                    ),
                    modifier = Modifier
                        .padding(iconPadding)
                        .background(
                            color = if (selected) {
                                NavButtonSelectedBackgroundColor
                            } else {
                                Color.Transparent
                            }
                        )
                )
            }
        }
    }
}


@Preview("main screen", widthDp = 768, heightDp = 1024)
@Composable
fun PreviewMainScreen() {
    MainView(
        GlobalRepository(),
        UserRepository()
    )
}

sealed class Screen(val route: String) {
    data object JobOrderCreation : Screen("job")
    data object Landing : Screen("landing")
    data object SearchOrders : Screen("search_orders")
    data object PMS : Screen("pms")
    data object PMSDetail : Screen("pms_detail")
    data object PackageDetail : Screen("package_detail")
    data object Checkout : Screen("checkout")
    data object PackageEdit : Screen("package_edit")
    data object SearchProduct : Screen("search_product")
    data object SearchProductResult : Screen("search_product_result")
    data object FullScreenImages : Screen("full_screen_images")
    data object ProductDetail : Screen("product_detail")
    data object Profile: Screen("profile")
    data object CheckList: Screen("check_list")
    data object SaveCheckList: Screen("save_check_list")
}
