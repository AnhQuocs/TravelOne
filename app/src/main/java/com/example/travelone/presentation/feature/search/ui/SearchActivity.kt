package com.example.travelone.presentation.feature.search.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AirplanemodeActive
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Hotel
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.travelone.BaseComponentActivity
import com.example.travelone.R
import com.example.travelone.domain.model.recent_viewed.ViewedType
import com.example.travelone.domain.model.search.SearchResultItem
import com.example.travelone.domain.usecase.search.suggestions.SearchSuggestionItem
import com.example.travelone.presentation.components.HotelCardHorizontal
import com.example.travelone.presentation.feature.flight.ui.FlightItem
import com.example.travelone.presentation.feature.recent_viewed.ui.RecentList
import com.example.travelone.presentation.feature.recent_viewed.viewmodel.RecentViewedViewModel
import com.example.travelone.presentation.feature.search.viewmodel.UnifiedSearchViewModel
import com.example.travelone.presentation.feature.search_history.ui.SearchHistoryList
import com.example.travelone.presentation.feature.search_history.viewmodel.SearchHistoryViewModel
import com.example.travelone.ui.theme.AppShape
import com.example.travelone.ui.theme.AppSpacing
import com.example.travelone.ui.theme.Dimens
import com.example.travelone.ui.theme.WeatherCardBlue
import dagger.hilt.android.AndroidEntryPoint
import java.util.UUID

@AndroidEntryPoint
class SearchActivity : BaseComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val navController = rememberNavController()

            NavHost(
                navController = navController,
                startDestination = "search"
            ) {
                composable("search") {
                    SearchScreen(
                        navController = navController,
                        onItemClick = {},
                        onBackClick = { finish() }
                    )
                }

                composable("search_result") {
                    SearchResultScreen(
                        navController = navController,
                        onBackClick = { navController.popBackStack() }
                    )
                }
            }
        }
    }
}

@Composable
fun SearchScreen(
    onBackClick: () -> Unit,
    onItemClick: () -> Unit,
    navController: NavController,
    unifiedSearchViewModel: UnifiedSearchViewModel = hiltViewModel(),
    recentViewedViewModel: RecentViewedViewModel = hiltViewModel(),
    searchHistoryViewModel: SearchHistoryViewModel = hiltViewModel()
) {
    val query = unifiedSearchViewModel.query
    val suggestions = unifiedSearchViewModel.suggestions
    val isLoading = unifiedSearchViewModel.isLoading
    val showSuggestions = unifiedSearchViewModel.showSuggestions

    val recentList = recentViewedViewModel.recentList
    val historyList = searchHistoryViewModel.historyList

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .background(color = Color.White)
            ) {
                SearchTopBar(
                    onBackClick = { onBackClick() },
                    onNotification = {}
                )

                Spacer(modifier = Modifier.height(AppSpacing.ExtraLarge))

                SearchBarSection(
                    query = query,
                    suggestions = suggestions,
                    onQueryChange = {
                        unifiedSearchViewModel.onQueryChanged(it)
                    },
                    onSearch = {
                        val currentQuery = unifiedSearchViewModel.query.trim()
                        if (currentQuery.isNotEmpty()) {
                            navController.currentBackStackEntry?.savedStateHandle?.set("search_query", currentQuery)
                            navController.navigate("search_result")
                        }
                    },
                    onSuggestionClick = {
                        unifiedSearchViewModel.onSuggestionClicked(it)
                        val generatedId = UUID.randomUUID().toString()

                        when (it) {
                            is SearchSuggestionItem.HotelSuggestion -> {
                                searchHistoryViewModel.addHistory(
                                    id = generatedId,
                                    title = it.name,
                                    subTitle = it.shortAddress
                                )
                                navController.currentBackStackEntry
                                    ?.savedStateHandle
                                    ?.set("search_result_data", SearchResultItem.HotelItem(it.hotel))
                                navController.navigate("search_result")
                            }
                            is SearchSuggestionItem.FlightSuggestion -> {
                                val flight = it.flight
                                val route = "${flight.departureAirportCode} → ${flight.arrivalAirportCode}"
                                searchHistoryViewModel.addHistory(
                                    id = generatedId,
                                    title = route,
                                    subTitle = "${flight.departureShortAddress} → ${flight.arrivalShortAddress}"
                                )
                                navController.currentBackStackEntry
                                    ?.savedStateHandle
                                    ?.set("search_result_data", SearchResultItem.FlightItem(flight))
                                navController.navigate("search_result")
                            }
                        }
                    },
                    showSuggestions = showSuggestions
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .background(Color.White)
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                if (isLoading) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                } else {
                    LazyColumn(
                        modifier = Modifier.padding(
                            horizontal = Dimens.PaddingM,
                            vertical = Dimens.PaddingS
                        )
                    ) {
                        if (recentList.isEmpty() && historyList.isEmpty()) {
                            item {
                                Text(
                                    text = stringResource(id = R.string.no_recent_items_yet),
                                    style = MaterialTheme.typography.titleMedium,
                                    modifier = Modifier.padding(vertical = Dimens.PaddingL)
                                )
                            }
                        } else {
                            item {
                                Spacer(modifier = Modifier.height(AppSpacing.Large))
                                SearchHistoryList(navController = navController)
                            }
                            item {
                                Spacer(modifier = Modifier.height(AppSpacing.Large))
                                RecentList(
                                    onHotelClick = {  },
                                    onFlightClick = {  }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SearchBarSection(
    query: String,
    suggestions: List<SearchSuggestionItem>,
    onQueryChange: (String) -> Unit,
    onSearch: () -> Unit,
    showSuggestions: Boolean,
    onSuggestionClick: (SearchSuggestionItem) -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(modifier = Modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = query,
            onValueChange = {
                onQueryChange(it)
            },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text(stringResource(id = R.string.search), color = Color.Gray) },
            shape = RoundedCornerShape(AppShape.ExtraExtraLargeShape),
            singleLine = true,
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_search),
                    contentDescription = null,
                    modifier = Modifier.size(Dimens.SizeM),
                    tint = Color.Gray
                )
            },
            trailingIcon = {
                if (query.isNotBlank()) {
                    IconButton(onClick = { onQueryChange("") }) {
                        Icon(Icons.Default.Close, contentDescription = "Clear")
                    }
                } else {
                    Icon(
                        imageVector = Icons.Default.Tune,
                        contentDescription = "Filter"
                    )
                }
            },
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = {
                onSearch()
                keyboardController?.hide()
            })
        )

        Spacer(modifier = Modifier.height(AppSpacing.Medium))

        if (showSuggestions && query.isNotBlank()) {
            if (suggestions.isEmpty()) {
                Text(
                    text = stringResource(id = R.string.no_suggestions_found),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(Dimens.PaddingM),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyMedium
                )
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = 300.dp)
                        .padding(vertical = Dimens.PaddingS)
                        .drawWithContent {
                            drawContent()
                            val shadowHeight = 8.dp.toPx()
                            drawRect(
                                brush = Brush.verticalGradient(
                                    colors = listOf(
                                        Color.Black.copy(alpha = 0.15f),
                                        Color.Transparent
                                    ),
                                    startY = size.height - shadowHeight,
                                    endY = size.height
                                ),
                                size = Size(size.width, shadowHeight),
                                topLeft = Offset(0f, size.height - shadowHeight)
                            )
                        }
                ) {
                    items(suggestions) { suggestion ->
                        when (suggestion) {
                            is SearchSuggestionItem.HotelSuggestion -> {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable(
                                            indication = rememberRipple(bounded = true, color = WeatherCardBlue),
                                            interactionSource = remember { MutableInteractionSource() }
                                        ) {
                                            onSuggestionClick(suggestion)
                                            keyboardController?.hide()
                                        }
                                        .padding(horizontal = Dimens.PaddingM, vertical = Dimens.PaddingSM),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Hotel,
                                        contentDescription = null,
                                        modifier = Modifier.size(Dimens.SizeSM)
                                    )
                                    Spacer(modifier = Modifier.width(AppSpacing.MediumLarge))
                                    Column {
                                        Text(text = suggestion.name, style = MaterialTheme.typography.bodyLarge)
                                        Text(
                                            text = suggestion.shortAddress,
                                            style = MaterialTheme.typography.bodySmall,
                                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                                        )
                                    }
                                }
                            }

                            is SearchSuggestionItem.FlightSuggestion -> {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable(
                                            indication = rememberRipple(bounded = true, color = WeatherCardBlue),
                                            interactionSource = remember { MutableInteractionSource() },
                                            onClick = {
                                                onSuggestionClick(suggestion)
                                                keyboardController?.hide()
                                            }
                                        )
                                        .padding(horizontal = Dimens.PaddingM, vertical = Dimens.PaddingSM)
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.AirplanemodeActive,
                                            contentDescription = null,
                                            modifier = Modifier.size(Dimens.SizeM)
                                        )

                                        Spacer(modifier = Modifier.width(AppSpacing.MediumLarge))

                                        Box(modifier = Modifier.width(100.dp), contentAlignment = Alignment.Center) {
                                            LocationColumn(suggestion.flight.departureShortAddress)
                                        }

                                        Icon(
                                            imageVector = Icons.Default.ArrowForward,
                                            contentDescription = null,
                                            modifier = Modifier
                                                .padding(horizontal = Dimens.PaddingSM)
                                                .size(Dimens.SizeSM)
                                        )

                                        Box(modifier = Modifier.width(100.dp), contentAlignment = Alignment.Center) {
                                            LocationColumn(suggestion.flight.arrivalShortAddress)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun LocationColumn(address: String) {
    val parts = address.split(",").map { it.trim() }
    Column {
        Text(
            text = parts.getOrNull(0) ?: "",
            style = MaterialTheme.typography.bodyLarge
        )
        parts.getOrNull(1)?.let {
            Text(
                text = it,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}