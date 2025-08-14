package com.example.travelone.presentation.feature.flight.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.travelone.R
import com.example.travelone.domain.model.flight.Flight
import com.example.travelone.presentation.components.formatDuration
import com.example.travelone.presentation.components.formatPrice
import com.example.travelone.ui.theme.AppShape
import com.example.travelone.ui.theme.AppSpacing
import com.example.travelone.ui.theme.Dimens
import com.example.travelone.ui.theme.JostTypography
import com.example.travelone.ui.theme.OceanBlue

@Composable
fun FlightCardHorizontal(flight: Flight, onClick: () -> Unit) {
    val displayPrice = formatPrice(flight.priceEconomy)

    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(AppShape.SmallShape),
        modifier = Modifier
            .height(Dimens.HeightXL)
            .padding(bottom = Dimens.PaddingS, top = Dimens.PaddingXSPlus)
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.Top
        ) {
            Row(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                verticalAlignment = Alignment.Top
            ) {
                AsyncImage(
                    model = flight.airlineEmblemUrl,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(0.28f)
                        .clip(RoundedCornerShape(AppShape.MediumShape)),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.width(AppSpacing.Small))

                Column(
                    modifier = Modifier.fillMaxHeight(),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = flight.airline + " " + flight.flightNumber,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                        style = JostTypography.titleSmall.copy(fontWeight = FontWeight.Bold),
                        color = Color.Black,
                        modifier = Modifier
                            .padding(start = Dimens.PaddingXS)
                            .fillMaxWidth()
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_location),
                            contentDescription = null,
                            colorFilter = ColorFilter.tint(color = Color.Black.copy(alpha = 0.6f)),
                            modifier = Modifier.size(Dimens.SizeM)
                        )

                        Text(
                            text = flight.departureAirportCode,
                            style = JostTypography.bodyMedium,
                            color = Color.Black.copy(alpha = 0.6f)
                        )

                        Spacer(modifier = Modifier.width(AppSpacing.Small))

                        Icon(
                            Icons.Default.ArrowForward,
                            contentDescription = null,
                            modifier = Modifier.size(20.dp)
                        )

                        Spacer(modifier = Modifier.width(AppSpacing.Small))

                        Text(
                            text = flight.arrivalAirportCode,
                            style = JostTypography.bodyMedium,
                            color = Color.Black.copy(alpha = 0.6f)
                        )

                        Spacer(modifier = Modifier.weight(1f))

                        Text(
                            text = formatDuration(flight.schedules[0].durationMinutes),
                            style = JostTypography.bodyMedium,
                            color = Color.Black.copy(alpha = 0.8f)
                        )
                    }

                    Text(
                        buildAnnotatedString {
                            withStyle(style = JostTypography.titleSmall.toSpanStyle().copy(color = OceanBlue, fontWeight = FontWeight.Bold)) {
                                append(displayPrice)
                            }
                            withStyle(style = JostTypography.titleSmall.toSpanStyle().copy(color = Color.Black)) {
                                append("/" + stringResource(id = R.string.person))
                            }
                        },
                        modifier = Modifier.padding(start = Dimens.PaddingXS)
                    )
                }
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.ic_transit_flight),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(color = Color.Black.copy(alpha = 0.8f)),
                    modifier = Modifier.size(Dimens.SizeM)
                )

                Spacer(modifier = Modifier.width(AppSpacing.Small))

                Text(
                    text = flight.stops.size.toString()
                )
            }
        }
    }
}