package com.example.travelone.presentation.feature.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
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
import com.example.travelone.presentation.feature.hotel.ui.HotelList
import com.example.travelone.presentation.feature.user.UserInfo
import com.example.travelone.presentation.feature.user.UserInfoShimmerLoading
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
                                .padding(horizontal = Dimens.PaddingM)
                                .padding(bottom = Dimens.PaddingM)
                                .fillMaxWidth()
                                .height(Dimens.HeightXL),
                            contentAlignment = Alignment.BottomCenter
                        ) {
                            UserInfoShimmerLoading()
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
                .fillMaxSize()
                .background(color = Color.White)
                .padding(paddingValues)
                .padding(Dimens.PaddingM)
        ) {
            item { HotelList() }
        }
    }
}