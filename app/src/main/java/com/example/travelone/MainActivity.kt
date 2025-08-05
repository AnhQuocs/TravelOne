package com.example.travelone

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.core.os.LocaleListCompat
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.travelone.data.preferences.language.LanguagePreferenceManager
import com.example.travelone.domain.model.language.AppLanguage
import com.example.travelone.presentation.feature.auth.viewmodel.SplashViewModel
import com.example.travelone.presentation.feature.dashboard.DashboardScreen
import com.example.travelone.presentation.feature.main.HomeScreen
import com.example.travelone.presentation.feature.main.MainScreen
import com.example.travelone.presentation.feature.main.RoomListTest
import com.example.travelone.presentation.feature.hotel.map.ui.FullMapScreen
import com.example.travelone.presentation.feature.hotel.ui.SearchScreen
import com.example.travelone.ui.theme.TravelOneTheme
import com.example.travelone.utils.LanguageManager
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import java.util.Locale

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val splashViewModel: SplashViewModel by viewModels()

    override fun attachBaseContext(newBase: Context) {
        val updatedContext = runBlocking {
            val manager = LanguagePreferenceManager(newBase)
            val lang = manager.languageFlow.first()

            val localeList = LocaleListCompat.create(Locale(lang.code))
            AppCompatDelegate.setApplicationLocales(localeList)

            LanguageManager.setAppLocale(newBase, lang)
        }
        super.attachBaseContext(updatedContext)
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        applySavedLanguage()

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
//                        MainScreenTest(navController = navController)
//                        HomeScreen(navHostController = navController)
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
                        FullMapScreen(latLng = LatLng(lat, lng))
                    }

                    composable("search") {
                        SearchScreen()
                    }
                }
            }
        }
    }

    private fun applySavedLanguage() = runBlocking {
        val manager = LanguagePreferenceManager(applicationContext)
        val lang = manager.languageFlow.firstOrNull() ?: AppLanguage.ENGLISH

        LanguageManager.setAppLocale(applicationContext, lang)
        AppCompatDelegate.setApplicationLocales(
            LocaleListCompat.create(Locale(lang.code))
        )
    }
}