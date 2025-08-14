package com.example.travelone.presentation.feature.search.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.travelone.R
import com.example.travelone.domain.model.recent_viewed.ViewedType
import com.example.travelone.domain.model.search.SearchResultItem
import com.example.travelone.presentation.components.AppLineGray
import com.example.travelone.presentation.components.HotelCardHorizontal
import com.example.travelone.presentation.components.TitleSection
import com.example.travelone.presentation.feature.flight.ui.FLightTicket
import com.example.travelone.presentation.feature.recent_viewed.viewmodel.RecentViewedViewModel
import com.example.travelone.presentation.feature.search.viewmodel.UnifiedSearchViewModel
import com.example.travelone.ui.theme.Dimens
import com.example.travelone.ui.theme.JostTypography

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun SearchResultScreen(
    onBackClick: () -> Unit,
    navController: NavController,
    unifiedSearchViewModel: UnifiedSearchViewModel = hiltViewModel(),
    recentViewedViewModel: RecentViewedViewModel = hiltViewModel()
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

    val scrollState = rememberLazyListState()
    val hasScrolled = remember {
        derivedStateOf {
            scrollState.firstVisibleItemIndex > 0 || scrollState.firstVisibleItemScrollOffset > 0
        }
    }

    LaunchedEffect(result) {
        result?.let {
            when (it) {
                is SearchResultItem.HotelItem -> {
                    unifiedSearchViewModel.onQueryChanged(it.hotel.name)
                    unifiedSearchViewModel.onSearch()
                }

                is SearchResultItem.FlightItem -> {
                    val route =
                        "${it.flight.departureAirportCode} → ${it.flight.arrivalAirportCode}"
                    unifiedSearchViewModel.onQueryChanged(route)
                    unifiedSearchViewModel.onSearch()
                }
            }
        }
    }

    Scaffold(
        topBar = {
            Surface(
                tonalElevation = if (hasScrolled.value) 4.dp else 0.dp,
                shadowElevation = if (hasScrolled.value) 4.dp else 0.dp,
            ) {
                TopAppBar(
                    title = {
                        FlowRow(
                            horizontalArrangement = Arrangement.spacedBy(Dimens.PaddingS),
                            maxItemsInEachRow = 2
                        ) {
                            Text(
                                text = stringResource(id = R.string.search_result_for),
                                style = JostTypography.titleMedium,
                                modifier = Modifier.align(Alignment.CenterVertically)
                            )
                            Text(
                                text = query ?: when (result) {
                                    is SearchResultItem.HotelItem -> result.hotel.name
                                    is SearchResultItem.FlightItem ->
                                        "${result.flight.departureAirportCode} → ${result.flight.arrivalAirportCode}"
                                    else -> stringResource(id = R.string.search_results)
                                },
                                style = JostTypography.titleSmall.copy(fontWeight = FontWeight.Bold),
                                modifier = Modifier.align(Alignment.CenterVertically)
                            )
                        }
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
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            when {
                isLoading -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(color = Color.White),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    }
                }

                searchResults.isEmpty() -> {
                    Text(
                        text = stringResource(id = R.string.no_results_found),
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                else -> {
                    LazyColumn(
                        state = scrollState,
                        modifier = Modifier
                            .background(color = Color.White)
                            .padding(horizontal = Dimens.PaddingM)
                            .fillMaxSize()
                    ) {
                        val hotelResults =
                            searchResults.filterIsInstance<SearchResultItem.HotelItem>()
                        val flightResults =
                            searchResults.filterIsInstance<SearchResultItem.FlightItem>()

                        if (hotelResults.isNotEmpty()) {
                            item {
                                TitleSection(
                                    text1 = stringResource(id = R.string.hotel),
                                    text2 = stringResource(id = R.string.see_all)
                                )
                            }

                            item {
                                Spacer(modifier = Modifier.height(Dimens.PaddingS))
                            }

                            itemsIndexed(hotelResults.take(3)) { index, hotelItem ->
                                HotelCardHorizontal(hotel = hotelItem.hotel, onClick = {
                                    recentViewedViewModel.addRecent(
                                        hotelItem.hotel.id,
                                        ViewedType.FLIGHT
                                    )
                                })

                                if (index < hotelResults.take(3).lastIndex) {
                                    AppLineGray(modifier = Modifier.padding(vertical = Dimens.PaddingS))
                                }
                            }
                        }

                        item {
                            Spacer(modifier = Modifier.height(Dimens.PaddingM))
                        }

                        if (flightResults.isNotEmpty()) {
                            item {
                                TitleSection(
                                    text1 = stringResource(id = R.string.flight),
                                    text2 = stringResource(id = R.string.see_all)
                                )
                            }

                            item {
                                Spacer(modifier = Modifier.height(Dimens.PaddingM))
                            }

                            items(flightResults.take(4)) { flightItem ->
                                FLightTicket(flight = flightItem.flight, onClick = {
                                    recentViewedViewModel.addRecent(
                                        flightItem.flight.id,
                                        ViewedType.FLIGHT
                                    )
                                })
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