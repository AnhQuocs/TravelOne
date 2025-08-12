package com.example.travelone.presentation.feature.flight.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.example.travelone.R
import com.example.travelone.domain.model.flight.Flight
import com.example.travelone.domain.model.flight.FlightSchedules
import com.example.travelone.domain.model.flight.FlightStops
import com.example.travelone.domain.model.recent_viewed.ViewedType
import com.example.travelone.presentation.components.AppLineGray
import com.example.travelone.presentation.components.formatDuration
import com.example.travelone.presentation.feature.recent_viewed.viewmodel.RecentViewedViewModel
import com.example.travelone.ui.theme.AppShape
import com.example.travelone.ui.theme.AppSpacing
import com.example.travelone.ui.theme.Dimens
import com.example.travelone.ui.theme.JostTypography
import com.example.travelone.ui.theme.TravelOneTheme
import java.time.OffsetTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

@Composable
fun FlightItem(
    flight: Flight,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = Dimens.PaddingM),
        shape = RoundedCornerShape(AppShape.LargeShape),
        color = Color.White,
        shadowElevation = 2.dp
    ) {
        Column {
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(flight.airlineLogoUrl)
                        .crossfade(true)
                        .diskCachePolicy(CachePolicy.ENABLED)
                        .memoryCachePolicy(CachePolicy.ENABLED)
                        .build(),
                    contentDescription = null,
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .size(width = 200.dp, height = 50.dp),
                    contentScale = ContentScale.Fit
                )
            }

            ConstraintLayout(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Dimens.PaddingM)
            ) {
                val (time, fromShortAddress, toShortAddress, line, stop) = createRefs()

                val firstPathDeparture = flight.departureShortAddress.substringBefore(",").trim()
                val firstPathArrival = flight.arrivalShortAddress.substringBefore(",").trim()
                val stopSize = flight.stops.size

                Text(
                    text = formatDuration(flight.schedules[0].durationMinutes),
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.constrainAs(time) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }.fillMaxWidth()
                )

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.constrainAs(fromShortAddress) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        bottom.linkTo(parent.bottom)
                    }
                ) {
                    Text(
                        text = firstPathDeparture,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(text = flight.departureAirportCode)
                }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.constrainAs(toShortAddress) {
                        top.linkTo(parent.top)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                    }
                ) {
                    Text(
                        text = firstPathArrival,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(text = flight.arrivalAirportCode)
                }

                Image(
                    painter = painterResource(id = R.drawable.line_airple_blue),
                    contentDescription = null,
                    modifier = Modifier
                        .constrainAs(line) {
                            start.linkTo(fromShortAddress.end, margin = Dimens.PaddingSM)
                            end.linkTo(toShortAddress.start, margin = Dimens.PaddingSM)
                            centerVerticallyTo(fromShortAddress)
                            width = Dimension.fillToConstraints
                        },
                    contentScale = ContentScale.FillWidth
                )

                if (stopSize != 0) {
                    Text(
                        text = "$stopSize ${stringResource(id = R.string.stop)}",
                        style = JostTypography.bodyMedium,
                        color = Color.Black.copy(alpha = 0.8f),
                        modifier = Modifier.constrainAs(stop) {
                            top.linkTo(line.bottom, margin = Dimens.PaddingS)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                    )
                }
            }

            AppLineGray(modifier = Modifier.padding(vertical = Dimens.PaddingM))

            Row {  }
        }
    }
}

fun convertToVietnamTime(timeString: String, outputPattern: String = "HH:mm"): String {
    return try {
        val offsetTime = OffsetTime.parse(timeString, DateTimeFormatter.ISO_OFFSET_TIME)
        val vietnamOffset = ZoneOffset.of("+07:00")
        val vietnamTime = offsetTime.withOffsetSameInstant(vietnamOffset)
        vietnamTime.format(DateTimeFormatter.ofPattern(outputPattern))
    } catch (e: Exception) {
        timeString
    }
}

@Preview(showBackground = true)
@Composable
private fun FlightItemPreview() {
    val sampleFlight = Flight(
        id = "FL123",
        airline = "Vietnam Airlines",
        flightNumber = "VN123",
        airlineLogoUrl = "https://res.cloudinary.com/dcucajyzg/image/upload/v1754737594/sing_airline_logo_mhs5rw.png",
        airlineEmblemUrl = "https://res.cloudinary.com/dcucajyzg/image/upload/v1754737594/sing_airline_logo_mhs5rw.png",
        departureAirportCode = "HAN",
        departureAirportName = "Noi Bai International Airport",
        departureShortAddress = "Hanoi, Vietnam",
        arrivalAirportCode = "SGN",
        arrivalAirportName = "Tan Son Nhat International Airport",
        arrivalShortAddress = "HCM City, Vietnam",
        numberOfFlightsInDay = 3,
        schedules = listOf(
            FlightSchedules(
                departureTime = "08:00",
                arrivalTime = "09:30",
                durationMinutes = 90,
                availableSeatsEconomy = 25,
                availableSeatsBusiness = 5
            ),
            FlightSchedules(
                departureTime = "13:00",
                arrivalTime = "15:00",
                durationMinutes = 120,
                availableSeatsEconomy = 10,
                availableSeatsBusiness = 2
            )
        ),
        priceEconomy = 150,
        priceBusiness = 350,
        numberOfStops = 1,
        stops = listOf(
            FlightStops(
                airportCode = "DAD",
                airportName = "Da Nang International Airport",
                stopDurationMinutes = 30,
                stopShortAddress = "Da Nang, Vietnam"
            )
        )
    )

    TravelOneTheme {
        FlightItem(
            flight = sampleFlight,
            onClick = {}
        )
    }
}