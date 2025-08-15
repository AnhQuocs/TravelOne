package com.example.travelone

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.travelone.presentation.feature.auth.viewmodel.SplashViewModel
import com.example.travelone.presentation.feature.dashboard.DashboardScreen
import com.example.travelone.presentation.feature.hotel.map.ui.FullMapScreen
import com.example.travelone.presentation.feature.main.MainScreen
import com.example.travelone.presentation.feature.main.RoomListTest
import com.example.travelone.ui.theme.TravelOneTheme
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseComponentActivity() {

    private val splashViewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TravelOneTheme {
                val navController = rememberNavController()
                val startDest by splashViewModel.startDestination.collectAsState()

                NavHost(navController = navController, startDestination = startDest) {
                    composable("dashboard") {
                        DashboardScreen(navHostController = navController)
                    }

                    composable("main") {
                        MainScreen( navHostController = navController)
                    }

                    composable(
                        route = "roomList/{hotelId}",
                        arguments = listOf(navArgument("hotelId") { type = NavType.StringType })
                    ) { backStackEntry ->
                        val hotelId = backStackEntry.arguments?.getString("hotelId") ?: ""
                        RoomListTest(hotelId = hotelId)
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