package com.example.travelone.presentation.feature.hotel.ui
//
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.items
//import androidx.compose.material3.CircularProgressIndicator
//import androidx.compose.material3.ExperimentalMaterial3Api
//import androidx.compose.material3.SearchBar
//import androidx.compose.material3.SearchBarDefaults
//import androidx.compose.material3.Text
//import androidx.compose.material3.TextFieldDefaults
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.unit.dp
//import androidx.hilt.navigation.compose.hiltViewModel
//import com.example.travelone.domain.model.hotel.Hotel
//import com.example.travelone.presentation.feature.hotel.viewmodel.HotelSearchViewModel
//
//@Composable
//fun SearchScreen(
//    hotelSearchViewModel: HotelSearchViewModel = hiltViewModel(),
//    onHotelClick: () -> Unit = {}
//) {
//    val searchResults = hotelSearchViewModel.searchResults
//    val suggestions = hotelSearchViewModel.suggestions
//    val isLoading by hotelSearchViewModel.isLoading
//
//    var query by remember { mutableStateOf("") }
//    var active by remember { mutableStateOf(false) }
//
//    Column(modifier = Modifier.fillMaxSize()) {
//        SearchBarSection(
//            query = query,
//            active = active,
//            suggestions = suggestions,
//            onQueryChange = {
//                query = it
//                hotelSearchViewModel.onQueryChanged(it)
//            },
//            onSearch = {
//                hotelSearchViewModel.onSearch(query)
//                active = false
//            },
//            onActiveChange = { active = it },
//            onSuggestionClick = {
//                query = it
//                hotelSearchViewModel.onSearch(it)
//                active = false
//            }
//        )
//
//        Box(modifier = Modifier.fillMaxSize()) {
//            if (isLoading) {
//                CircularProgressIndicator(
//                    modifier = Modifier.align(Alignment.Center)
//                )
//            } else {
//                LazyColumn {
//                    items(searchResults) { hotel ->
//                        HotelCard(hotel = hotel, onClick = onHotelClick)
//                    }
//                }
//            }
//        }
//    }
//}
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun SearchBarSection(
//    query: String,
//    active: Boolean,
//    suggestions: List<String>,
//    onQueryChange: (String) -> Unit,
//    onSearch: () -> Unit,
//    onActiveChange: (Boolean) -> Unit,
//    onSuggestionClick: (String) -> Unit
//) {
//    SearchBar(
//        query = query,
//        onQueryChange = onQueryChange,
//        onSearch = { onSearch() },
//        active = active,
//        onActiveChange = onActiveChange,
//        placeholder = { Text("Search hotels or cities") },
//        colors = SearchBarDefaults.colors(
//            containerColor = Color.White,
//            dividerColor = Color.LightGray,
//            inputFieldColors = TextFieldDefaults.colors(
//                focusedTextColor = Color.Black,
//                unfocusedTextColor = Color.Black,
//                cursorColor = Color.Black,
//                focusedPlaceholderColor = Color.Gray,
//                unfocusedPlaceholderColor = Color.Gray
//            )
//        ),
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(8.dp)
//    ) {
//        LazyColumn {
//            items(suggestions) { suggestion ->
//                Text(
//                    text = suggestion,
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .clickable { onSuggestionClick(suggestion) }
//                        .padding(16.dp)
//                )
//            }
//        }
//    }
//}
//
//
//@Composable
//fun HotelCard(hotel: Hotel, onClick: () -> Unit) {
//    TODO("Not yet implemented")
//}