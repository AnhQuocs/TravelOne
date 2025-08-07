package com.example.travelone.fake.flight

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.travelone.ui.theme.Dimens

@Composable
fun FlightCard(flight: Flight, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "${flight.airline} - ${flight.price} VND", style = MaterialTheme.typography.titleMedium)
            Text(text = "${flight.departureCity} → ${flight.destinationCity}")
            Text(text = "${flight.departureTime} - ${flight.arrivalTime}")
        }
    }
}