package com.example.travelone.presentation.feature.flight.ui

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.example.travelone.R
import com.example.travelone.domain.model.flight.Flight
import com.example.travelone.domain.model.flight.FlightSchedules
import com.example.travelone.domain.model.flight.FlightStops
import com.example.travelone.presentation.components.AppLineGray
import com.example.travelone.presentation.components.formatDuration
import com.example.travelone.presentation.components.formatPrice
import com.example.travelone.presentation.components.getUsdToVndRate
import com.example.travelone.ui.theme.AppShape
import com.example.travelone.ui.theme.AppSpacing
import com.example.travelone.ui.theme.Dimens
import com.example.travelone.ui.theme.JostTypography
import com.example.travelone.ui.theme.TravelOneTheme
import java.time.OffsetTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

@Composable
fun FLightTicket(
    flight: Flight,
    onClick: () -> Unit
) {
    val firstPathDeparture = flight.departureShortAddress.substringBefore(",").trim()
    val firstPathArrival = flight.arrivalShortAddress.substringBefore(",").trim()

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = Dimens.PaddingM)
            .clickable { onClick() },
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

            Spacer(modifier = Modifier.height(AppSpacing.Medium))

            Text(
                text = formatDuration(flight.schedules[0].durationMinutes),
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
            )

            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = Dimens.PaddingM),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = firstPathDeparture,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = flight.departureAirportCode,
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                }

                Image(
                    painter = painterResource(id = R.drawable.line_airple_blue),
                    contentDescription = null,
                    modifier = Modifier.fillMaxWidth(0.7f),
                    contentScale = ContentScale.FillWidth
                )

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = firstPathArrival,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = flight.arrivalAirportCode,
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                }
            }

            Text(
                text = "${flight.numberOfStops} ${stringResource(id = R.string.stop)}",
                style = JostTypography.bodyMedium,
                color = Color.Black.copy(alpha = 0.8f),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            AppLineGray(modifier = Modifier.padding(vertical = Dimens.PaddingM))

            val iconList = listOf(
                R.drawable.ic_seat,
                R.drawable.ic_bell,
                R.drawable.ic_wifi,
                R.drawable.ic_outlet
            )

            val textList = listOf(
                R.string.seat_pitch,
                R.string.light_meal,
                R.string.chance_wifi,
                R.string.power_outlet
            )

            val utilities = iconList.zip(textList)

            Column(
                modifier = Modifier.padding(horizontal = Dimens.PaddingM)
            ) {
                for (i in utilities.indices step 2) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(AppSpacing.Medium),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        FlightUtility(
                            iconRes = utilities[i].first,
                            text = stringResource(id = utilities[i].second),
                            modifier = Modifier.weight(1f)
                        )

                        if (i + 1 < utilities.size) {
                            FlightUtility(
                                iconRes = utilities[i + 1].first,
                                text = stringResource(id = utilities[i + 1].second),
                                modifier = Modifier.weight(1f)
                            )
                        } else {
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }

                    Spacer(modifier = Modifier.height(AppSpacing.Large))
                }
            }

            AppLineGray(modifier = Modifier.padding(bottom = Dimens.PaddingM))

            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .padding(horizontal = Dimens.PaddingM)
                    .padding(bottom = Dimens.PaddingM)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(R.drawable.ic_economy_seat),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(Color.Black.copy(alpha = 0.7f)),
                        modifier = Modifier.size(Dimens.SizeML)
                    )

                    Spacer(modifier = Modifier.width(AppSpacing.Medium))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = stringResource(id = R.string.economy_class_price) + ": ",
                            style = JostTypography.labelLarge.copy(fontSize = 15.sp, fontWeight = FontWeight.SemiBold),
                            color = Color.Black.copy(alpha = 0.7f)
                        )

                        Text(
                            text = formatPrice(flight.priceEconomy),
                            style = MaterialTheme.typography.bodyMedium.copy(fontSize = 15.sp, fontWeight = FontWeight.Bold)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(AppSpacing.Medium))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(modifier = Modifier.width(AppSpacing.ExtraSmall + 1.dp))
                    Image(
                        painter = painterResource(R.drawable.ic_business_seat),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(Color.Black.copy(alpha = 0.7f)),
                        modifier = Modifier.size(Dimens.SizeM)
                    )

                    Spacer(modifier = Modifier.width(AppSpacing.Medium))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = stringResource(id = R.string.business_class_price) + ": ",
                            style = JostTypography.labelLarge.copy(fontSize = 15.sp, fontWeight = FontWeight.SemiBold),
                            color = Color.Black.copy(alpha = 0.7f)
                        )

                        Text(
                            text = formatPrice(flight.priceBusiness),
                            style = MaterialTheme.typography.bodyMedium.copy(fontSize = 15.sp, fontWeight = FontWeight.Bold)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun FlightUtility(
    @DrawableRes iconRes: Int,
    text: String,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Image(
            painter = painterResource(iconRes),
            contentDescription = null,
            colorFilter = ColorFilter.tint(Color.Black.copy(alpha = 0.6f)),
            modifier = Modifier.size(Dimens.SizeSM)
        )

        Spacer(modifier = Modifier.width(AppSpacing.Medium))

        Text(
            text = text,
            style = MaterialTheme.typography.labelSmall,
            color = Color.Black.copy(alpha = 0.6f)
        )
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
        FLightTicket(
            flight = sampleFlight,
            onClick = {}
        )
    }
}