package com.example.travelone.presentation.feature.main

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.travelone.BaseComponentActivity
import com.example.travelone.presentation.feature.auth.viewmodel.SplashViewModel
import com.example.travelone.presentation.feature.dashboard.DashboardScreen
import com.example.travelone.presentation.feature.hotel.map.ui.FullMapScreen
import com.example.travelone.presentation.feature.hotel.ui.detail.HotelDetailSection
import com.example.travelone.ui.theme.TravelOneTheme
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseComponentActivity() {

    private val splashViewModel: SplashViewModel by viewModels()

    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TravelOneTheme {
                val navController = rememberNavController()
                val startDest by splashViewModel.startDestination.collectAsState()
                val slideDuration = 200

                AnimatedNavHost(navController = navController, startDestination = startDest) {
                    composable("dashboard") {
                        DashboardScreen(navHostController = navController)
                    }

                    composable("main") {
                        MainScreen( navHostController = navController)
                    }

                    composable(
                        "detail/{hotelId}",
                        arguments = listOf(navArgument("hotelId") {type = NavType.StringType}),
                        enterTransition = {
                            slideInHorizontally(initialOffsetX = { fullWidth -> fullWidth }, animationSpec = tween(slideDuration))
                        },
                        exitTransition = {
                            slideOutHorizontally(targetOffsetX = { fullWidth -> -fullWidth / 2 }, animationSpec = tween(slideDuration))
                        },
                        popEnterTransition = {
                            slideInHorizontally(initialOffsetX = { fullWidth -> -fullWidth }, animationSpec = tween(slideDuration))
                        },
                        popExitTransition = {
                            slideOutHorizontally(targetOffsetX = { fullWidth -> fullWidth }, animationSpec = tween(slideDuration))
                        }
                    ) { backStackEntry ->
                        val hotelId = backStackEntry.arguments?.getString("hotelId") ?: ""
                        HotelDetailSection(hotelId = hotelId, navHostController = navController)
                    }

                    composable(
                        route = "full_map/{lat}/{lng}",
                        arguments = listOf(
                            navArgument("lat") { type = NavType.StringType },
                            navArgument("lng") { type = NavType.StringType },
                        )
                    ) { backStackEntry ->
                        val lat = backStackEntry.arguments?.getString("lat")?.toDoubleOrNull() ?: 0.0
                        val lng = backStackEntry.arguments?.getString("lng")?.toDoubleOrNull() ?: 0.0

                        FullMapScreen(navHostController = navController, latLng = LatLng(lat, lng))
                    }

                    composable("search") {
//                        SearchScreen()
                    }
                }
            }
        }
    }
}