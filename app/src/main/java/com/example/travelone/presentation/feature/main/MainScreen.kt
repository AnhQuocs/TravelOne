package com.example.travelone.presentation.feature.main

import androidx.activity.ComponentActivity
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.travelone.R
import com.example.travelone.domain.model.language.AppLanguage
import com.example.travelone.presentation.components.BottomAppBar
import com.example.travelone.presentation.feature.auth.viewmodel.AuthViewModel
import com.example.travelone.presentation.feature.booking.MyBookingScreen
import com.example.travelone.presentation.feature.favorite.FavoriteScreen
import com.example.travelone.presentation.feature.hotel.ui.HotelList
import com.example.travelone.presentation.feature.hotel.ui.HotelRecommendedList
import com.example.travelone.presentation.feature.hotel.viewmodel.HotelViewModel
import com.example.travelone.presentation.feature.hotel.map.ui.MiniMap
import com.example.travelone.presentation.feature.hotel.map.viewmodel.LocationViewModel
import com.example.travelone.presentation.feature.profile.ProfileScreen
import com.example.travelone.presentation.feature.user.UserInfo
import com.example.travelone.presentation.feature.user.UserInfoShimmerLoading
import com.example.travelone.presentation.feature.weather.ui.WeatherSection
import com.example.travelone.presentation.feature.weather.viewmodel.WeatherViewModel
import com.example.travelone.presentation.language.LanguageViewModel
import com.example.travelone.ui.theme.Dimens
import com.example.travelone.utils.LangUtils

@Composable
fun MainScreen(navHostController: NavHostController) {
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    var previousTabIndex by remember { mutableIntStateOf(0) }

    Scaffold(
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.White)
            )
        },
        bottomBar = {
            BottomAppBar(
                currentIndex = selectedTabIndex,
                onTabSelected = { newIndex ->
                    previousTabIndex = selectedTabIndex
                    selectedTabIndex = newIndex
                }
            )
        }
    ) { paddingValues ->

        val isForward = selectedTabIndex > previousTabIndex

        AnimatedContent(
            targetState = selectedTabIndex,
            label = "TabTransition",
            transitionSpec = {
                if (isForward) {
                    (slideInHorizontally(
                        initialOffsetX = { width -> width },
                        animationSpec = tween(durationMillis = 200)
                    ) + fadeIn(
                        animationSpec = tween(durationMillis = 200)
                    )).togetherWith(
                        slideOutHorizontally(
                            targetOffsetX = { width -> -width },
                            animationSpec = tween(durationMillis = 200)
                        )
                    )
                } else {
                    (slideInHorizontally(
                        initialOffsetX = { width -> -width },
                        animationSpec = tween(durationMillis = 200)
                    ) + fadeIn(
                        animationSpec = tween(durationMillis = 200)
                    )).togetherWith(
                        slideOutHorizontally(
                            targetOffsetX = { width -> width },
                            animationSpec = tween(durationMillis = 200)
                        )
                    )
                }.using(
                    SizeTransform(clip = false)
                )
            },
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) { tab ->
            when (tab) {
                0 -> HomeScreen(navHostController)
                1 -> MyBookingScreen()
                2 -> FavoriteScreen()
                3 -> ProfileScreen()
            }
        }
    }
}

@Composable
fun HomeScreen(
    navHostController: NavHostController,
    authViewModel: AuthViewModel = hiltViewModel(),
    hotelViewModel: HotelViewModel = hiltViewModel(),
    weatherViewModel: WeatherViewModel = hiltViewModel(),
    locationViewModel: LocationViewModel = hiltViewModel(),
    languageViewModel: LanguageViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val activity = context as? ComponentActivity
    val currentLang by languageViewModel.currentLanguage.collectAsState()
    var lastLang by remember { mutableStateOf(currentLang) }

    val authState by authViewModel.authState.collectAsState()
    val isUserLoading by authViewModel.isUserLoading.collectAsState()

    val user = authState?.getOrNull()

    val weather = weatherViewModel.weather

    val userLocation by locationViewModel.userLocation.collectAsState()

    val scrollState = rememberLazyListState()
    val hasScrolled = remember {
        derivedStateOf {
            scrollState.firstVisibleItemIndex > 0 || scrollState.firstVisibleItemScrollOffset > 0
        }
    }

    LaunchedEffect(currentLang) {
        if (currentLang != lastLang) {
            lastLang = currentLang
            activity?.recreate()
        }
    }

    LaunchedEffect(Unit) {
        hotelViewModel.loadHotels()
        hotelViewModel.loadRecommendedHotels()
        locationViewModel.loadUserLocation()
    }

    Scaffold(
        topBar = {
            Surface(
                tonalElevation = if (hasScrolled.value) 4.dp else 0.dp,
                shadowElevation = if (hasScrolled.value) 4.dp else 0.dp,
            ) {
                val isWeatherLoading = weather == null

                when {
                    isUserLoading || isWeatherLoading -> {
                        Box(
                            modifier = Modifier
                                .background(color = Color.White)
                                .padding(horizontal = Dimens.PaddingM)
                                .fillMaxWidth()
                                .height(Dimens.HeightXL),
                            contentAlignment = Alignment.BottomCenter
                        ) {
                            UserInfoShimmerLoading()
                        }
                    }
                    user != null && weather != null -> {
                        UserInfo(
                            user = user,
                            weather = weather,
                            onSearch = {},
                            onOpenNotification = {}
                        )
                    }
                }
            }
        }
    ) { paddingValues ->
        LazyColumn (
            state = scrollState,
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.White)
                .padding(paddingValues)
                .padding(horizontal = Dimens.PaddingM)
        ) {
            item { Spacer(modifier = Modifier.height(Dimens.PaddingM)) }
            item { WeatherSection(context = context) }
            item { HotelList() }
            item { Spacer(modifier = Modifier.height(Dimens.PaddingL)) }
            item { HotelRecommendedList() }
            item { Spacer(modifier = Modifier.height(Dimens.PaddingL)) }
            item {
                MiniMap(
                    userLocation = userLocation,
                    onOpenMapClicked = { latLng ->
                        navHostController.navigate("full_map/${latLng.latitude}/${latLng.longitude}")
                    }
                )
            }
            item {
                Column(modifier = Modifier
                    .padding(16.dp)
                    .padding(top = 48.dp)) {
                    Button(onClick = {
                        LangUtils.currentLang = AppLanguage.ENGLISH.code
                        languageViewModel.changeLanguage(AppLanguage.ENGLISH)
                    }) {
                        Text("English 🇬🇧")
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Button(onClick = {
                        LangUtils.currentLang = AppLanguage.VIETNAMESE.code
                        languageViewModel.changeLanguage(AppLanguage.VIETNAMESE)
                    }) {
                        Text("Tiếng Việt 🇻🇳")
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(text = stringResource(id = R.string.welcome))
                }
            }
        }
    }
}