package com.example.travelone.presentation.feature.main

import android.util.Log
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.travelone.presentation.feature.auth.viewmodel.AuthViewModel
import com.example.travelone.presentation.language.LanguageViewModel
import com.example.travelone.ui.theme.Dimens

@Composable
fun MainScreen(
    navHostController: NavHostController,
    authViewModel: AuthViewModel = hiltViewModel(),
    hotelViewModel: AuthViewModel = hiltViewModel(),
    languageViewModel: LanguageViewModel = hiltViewModel()
) {
    val authState by authViewModel.authState.collectAsState()
    val isUserLoading by authViewModel.isUserLoading.collectAsState()

    val user = authState?.getOrNull()

    val scrollState = rememberLazyListState()
    val hasScrolled = remember {
        derivedStateOf {
            scrollState.firstVisibleItemIndex > 0 || scrollState.firstVisibleItemScrollOffset > 0
        }
    }

    Scaffold(
        topBar = {
            Surface(
                tonalElevation = if (hasScrolled.value) 4.dp else 0.dp,
                shadowElevation = if (hasScrolled.value) 4.dp else 0.dp,
            ) {
                when {
                    isUserLoading -> {
                        Box(
                            modifier = Modifier
                                .background(color = Color.White)
                                .padding(horizontal = Dimens.MediumPadding)
                                .padding(bottom = Dimens.MediumPadding)
                                .fillMaxWidth()
                                .height(Dimens.ExtraLargeHeight),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                    user != null -> {
                        UserInfo(
                            user = user,
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
                .background(color = Color.White)
                .padding(paddingValues)
                .padding(Dimens.MediumPadding)
        ) {
            item {
                HotelCardItem(title = "Hotel 1", price = "$120/night")
            }

            item {
                HotelMapSection()
            }

            item {
                HotelMapSection()
            }

            item {
                HotelBestTodaySection()
            }
        }
    }
}

@Composable
fun HotelCardItem(title: String, price: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Box(
            modifier = Modifier
                .height(150.dp)
                .fillMaxWidth()
                .background(Color.Gray)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(title, style = MaterialTheme.typography.titleMedium)
        Text(price, style = MaterialTheme.typography.bodySmall, color = Color.Blue)
    }
}

@Composable
fun HotelMapSection() {
    Column(modifier = Modifier.padding(16.dp)) {
        Text("Hotel Near You", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(8.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .background(Color.LightGray)
        )
    }
}

@Composable
fun HotelBestTodaySection() {
    Column(modifier = Modifier.padding(16.dp)) {
        Text("Best Today 🔥", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(8.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .background(Color.LightGray)
        )
    }
}