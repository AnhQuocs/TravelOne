package com.example.travelone.presentation.feature.hotel.map.ui

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.travelone.R
import com.example.travelone.domain.model.hotel.Hotel
import com.example.travelone.presentation.feature.hotel.viewmodel.HotelViewModel
import com.example.travelone.ui.theme.AppShape
import com.example.travelone.ui.theme.AppSpacing
import com.example.travelone.ui.theme.Dimens
import com.example.travelone.ui.theme.JostTypography
import com.example.travelone.ui.theme.OceanBlue
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.maps.android.compose.Circle
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.launch

@Composable
fun FullMapScreen(
    navHostController: NavHostController,
    latLng: LatLng,
    hotelViewModel: HotelViewModel = hiltViewModel()
) {
    val hotels = hotelViewModel.hotels
    val context = LocalContext.current

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(latLng, 14f)
    }

    val hotelBitmap = remember {
        bitmapFromVector(context, R.drawable.ic_hotel_marker, 80)
    }

    var selectedHotel by remember { mutableStateOf<Hotel?>(null) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        hotelViewModel.loadHotels()
    }

    LaunchedEffect(latLng) {
        cameraPositionState.animate(
            CameraUpdateFactory.newLatLngZoom(latLng, 4f)
        )
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        GoogleMap(
            cameraPositionState = cameraPositionState,
            modifier = Modifier.fillMaxSize()
        ) {
            Marker(
                state = MarkerState(position = latLng),
                title = "Your Location",
                snippet = null,
                onClick = {
                    coroutineScope.launch {
                        cameraPositionState.animate(
                            CameraUpdateFactory.newLatLngZoom(
                                latLng,
                                16f
                            )
                        )
                    }

                    true
                }
            )

            hotels.forEach { hotel ->
                val pos = LatLng(hotel.latitude, hotel.longitude)
                Marker(
                    state = MarkerState(position = pos),
                    title = hotel.name,
                    icon = BitmapDescriptorFactory.fromBitmap(hotelBitmap),
                    onClick = {
                        selectedHotel = hotel

                        coroutineScope.launch {
                            cameraPositionState.animate(
                                CameraUpdateFactory.newLatLngZoom(
                                    LatLng(hotel.latitude, hotel.longitude),
                                    16f
                                )
                            )
                        }

                        true
                    }
                )
            }
        }

        MapTopBar(
            onBackClick = {
                navHostController.popBackStack()
            },
            modifier = Modifier
                .align(Alignment.TopCenter)
                .fillMaxWidth()
                .zIndex(1f)
                .padding(top = Dimens.PaddingUltra)
        )

        selectedHotel?.let { hotel ->
            MapHotelInfoCard(
                modifier = Modifier.align(Alignment.BottomCenter),
                hotel = hotel,
                onCloseClick = {
                    selectedHotel = null

                    coroutineScope.launch {
                        cameraPositionState.animate(
                            CameraUpdateFactory.newLatLngZoom(LatLng(hotel.latitude, hotel.longitude), 3.5f)
                        )
                    }
                },
                onBookingClick = {},
                onContactClick = {}
            )
        }
    }
}

@Composable
fun MapHotelInfoCard(
    hotel: Hotel,
    onCloseClick: () -> Unit,
    onBookingClick: () -> Unit,
    onContactClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(Dimens.HeightXL3)
            .padding(Dimens.PaddingM),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(Dimens.PaddingM)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.weight(1f),
                    verticalAlignment = Alignment.Top
                ) {
                    AsyncImage(
                        model = hotel.thumbnailUrl,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth(0.28f)
                            .aspectRatio(1f)
                            .clip(RoundedCornerShape(AppShape.MediumShape)),
                        contentScale = ContentScale.Crop
                    )

                    Spacer(modifier = Modifier.width(AppSpacing.Medium))

                    Column(
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = hotel.name,
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
                                colorFilter = ColorFilter.tint(color = Color.Black.copy(alpha = 0.4f)),
                                modifier = Modifier.size(Dimens.SizeM)
                            )

                            Text(
                                text = hotel.shortAddress,
                                style = JostTypography.labelLarge,
                                color = Color.Black.copy(alpha = 0.4f)
                            )
                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                buildAnnotatedString {
                                    withStyle(style = JostTypography.titleSmall.toSpanStyle().copy(color = OceanBlue, fontWeight = FontWeight.Bold)) {
                                        append("$" + hotel.pricePerNightMin.toString())
                                    }
                                    withStyle(style = JostTypography.titleSmall.toSpanStyle().copy(color = Color.Black)) {
                                        append("/" + stringResource(id = R.string.night))
                                    }
                                },
                                modifier = Modifier.padding(start = Dimens.PaddingXS)
                            )

                            Spacer(modifier = Modifier.width(AppSpacing.Medium))

                            Text(
                                text = "⭐${hotel.averageRating}",
                                style = JostTypography.titleSmall.copy(fontWeight = FontWeight.Bold),
                                color = Color.Black,
                                modifier = Modifier
                                    .align(Alignment.Top)
                                    .padding(start = AppSpacing.Small)
                            )
                        }
                    }
                }

                Icon(
                    Icons.Default.Close,
                    contentDescription = null,
                    tint = Color.Black.copy(0.8f),
                    modifier = Modifier
                        .size(Dimens.SizeML)
                        .clickable { onCloseClick() }
                )
            }

            Spacer(modifier = Modifier.height(AppSpacing.ExtraLarge))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    modifier = Modifier
                        .height(Dimens.HeightDefault)
                        .fillMaxWidth(0.75f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = OceanBlue
                    ),
                    shape = RoundedCornerShape(AppShape.MediumShape),
                    onClick = { onBookingClick() }
                ) {
                    Text(
                        text = stringResource(id = R.string.booking_now),
                        style = JostTypography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                        color = Color.White
                    )
                }

                IconButton(
                    onClick = { onContactClick() },
                    modifier = Modifier
                        .border(1.dp, Color.LightGray, CircleShape)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_contact),
                        contentDescription = null,
                        modifier = Modifier.size(Dimens.SizeM)
                    )
                }
            }
        }
    }
}

fun bitmapFromVector(context: Context, vectorResId: Int, size: Int): Bitmap {
    val vectorDrawable = ContextCompat.getDrawable(context, vectorResId)
        ?: throw IllegalArgumentException("Drawable not found")
    val bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    vectorDrawable.setBounds(0, 0, canvas.width, canvas.height)
    vectorDrawable.draw(canvas)
    return bitmap
}