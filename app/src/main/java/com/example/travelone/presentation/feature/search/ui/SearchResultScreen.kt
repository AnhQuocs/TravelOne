package com.example.travelone.presentation.feature.search.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.travelone.R
import com.example.travelone.domain.model.search.SearchResultItem
import com.example.travelone.presentation.components.HotelCardHorizontal
import com.example.travelone.presentation.feature.flight.ui.FlightItem
import com.example.travelone.presentation.feature.search.viewmodel.UnifiedSearchViewModel
import com.example.travelone.ui.theme.Dimens
import com.example.travelone.ui.theme.JostTypography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchResultScreen(
    onBackClick: () -> Unit,
    navController: NavController,
    unifiedSearchViewModel: UnifiedSearchViewModel = hiltViewModel()
) {
    val result = navController.previousBackStackEntry
        ?.savedStateHandle
        ?.get<SearchResultItem>("search_result_data")

    val query = navController.previousBackStackEntry?.savedStateHandle?.get<String>("search_query")

    LaunchedEffect(query) {
        query?.let {
            unifiedSearchViewModel.onQueryChanged(it)
            unifiedSearchViewModel.onSearch()
        }
    }

    val searchResults = unifiedSearchViewModel.searchResults
    val isLoading = unifiedSearchViewModel.isLoading

    LaunchedEffect(result) {
        result?.let {
            when (it) {
                is SearchResultItem.HotelItem -> {
                    unifiedSearchViewModel.onQueryChanged(it.hotel.name)
                    unifiedSearchViewModel.onSearch()
                }
                is SearchResultItem.FlightItem -> {
                    val route = "${it.flight.departureAirportCode} → ${it.flight.arrivalAirportCode}"
                    unifiedSearchViewModel.onQueryChanged(route)
                    unifiedSearchViewModel.onSearch()
                }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = query ?: when (result) {
                            is SearchResultItem.HotelItem -> result.hotel.name
                            is SearchResultItem.FlightItem -> "${result.flight.departureAirportCode} → ${result.flight.arrivalAirportCode}"
                            else -> stringResource(id = R.string.search_results)
                        },
                        style = JostTypography.titleSmall,
                        modifier = Modifier.padding(start = Dimens.PaddingS)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_back),
                            contentDescription = null,
                            modifier = Modifier
                                .size(Dimens.SizeML)
                        )
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = Color.White
                )
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            when {
                isLoading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                searchResults.isEmpty() -> {
                    Text(
                        text = stringResource(id = R.string.no_results_found),
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                else -> {
                    LazyColumn(
                        modifier = Modifier
                            .padding(horizontal = Dimens.PaddingM)
                            .fillMaxSize()
                            .background(color = Color.White)
                    ) {
                        val hotelResults = searchResults.filterIsInstance<SearchResultItem.HotelItem>()
                        val flightResults = searchResults.filterIsInstance<SearchResultItem.FlightItem>()

                        if (hotelResults.isNotEmpty()) {
                            item { Text(text = "Hotels") }
                            items(hotelResults) { hotelItem ->
                                HotelCardHorizontal(hotel = hotelItem.hotel, onClick = {  })
                            }
                        }

                        if (flightResults.isNotEmpty()) {
                            item {
                                Text(
                                    text = stringResource(id = R.string.flight),
                                    style = JostTypography.titleSmall.copy(fontWeight = FontWeight.SemiBold)
                                )
                            }
                            items(flightResults) { flightItem ->
                                FlightItem(flight = flightItem.flight, onClick = {  })
                            }
                        }

                        if (hotelResults.isEmpty() && flightResults.isEmpty()) {
                            item { Text(text = "No results found") }
                        }
                    }
                }
            }
        }
    }
}