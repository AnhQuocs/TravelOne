package com.example.travelone.presentation.feature.hotel.ui.room

import android.content.Intent
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AspectRatio
import androidx.compose.material.icons.filled.Bathroom
import androidx.compose.material.icons.filled.Bed
import androidx.compose.material.icons.filled.BedroomParent
import androidx.compose.material.icons.filled.Layers
import androidx.compose.material.icons.filled.PeopleAlt
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material.icons.filled.SmokeFree
import androidx.compose.material.icons.filled.SmokingRooms
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.imageLoader
import coil.request.ImageRequest
import com.example.travelone.R
import com.example.travelone.domain.model.room.Amenity
import com.example.travelone.domain.model.room.Room
import com.example.travelone.presentation.components.AppLineGray
import com.example.travelone.presentation.components.ReadMoreText
import com.example.travelone.presentation.components.TitleSection
import com.example.travelone.presentation.components.formatPrice
import com.example.travelone.ui.theme.AppShape
import com.example.travelone.ui.theme.AppSpacing
import com.example.travelone.ui.theme.Dimens
import com.example.travelone.ui.theme.Green500
import com.example.travelone.ui.theme.JostTypography
import com.example.travelone.ui.theme.OceanBlue

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun RoomDetailScreen(
    onBack: () -> Unit,
    selectedRoom: Room,
    transitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope
) {
    BackHandler { onBack() }

    val context = LocalContext.current
    val imageLoader = context.imageLoader

    with(transitionScope) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .sharedBounds(
                    rememberSharedContentState(key = selectedRoom.id),
                    animatedVisibilityScope = animatedVisibilityScope
                )
        ) {
            // Header cố định
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(Dimens.HeightXXL + 20.dp)
                    .align(Alignment.TopCenter)
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(context)
                        .data(selectedRoom.imageUrl)
                        .crossfade(true)
                        .build(),
                    imageLoader = imageLoader,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                Box(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(Dimens.PaddingM)
                        .padding(top = Dimens.PaddingL)
                        .size(Dimens.SizeXL)
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.8f))
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) { onBack() },
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_back),
                        contentDescription = "Back",
                        colorFilter = ColorFilter.tint(Color.Black),
                        modifier = Modifier.size(Dimens.SizeM)
                    )
                }
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = Dimens.HeightXXL + 20.dp)
            ) {
                item { RoomInfo(selectedRoom) }
            }

            Button(
                onClick = {},
                modifier = Modifier
                    .padding(Dimens.PaddingM)
                    .padding(vertical = Dimens.PaddingM)
                    .align(Alignment.BottomEnd)
                    .shadow(elevation = 4.dp, shape = RoundedCornerShape(AppShape.LargeShape)),
                shape = RoundedCornerShape(AppShape.LargeShape),
                colors = ButtonDefaults.buttonColors(
                    containerColor = OceanBlue
                )
            ) {
                Text(
                    text = stringResource(id = R.string.booking_now),
                    color = Color.White,
                    modifier = Modifier.padding(vertical = Dimens.PaddingS)
                )
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun RoomInfo(
    room: Room
) {
    val displayPrice = formatPrice(room.pricePerNight)
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(Dimens.PaddingSM)
    ) {
        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            maxItemsInEachRow = 2
        ) {
            Text(
                text = room.roomType,
                style = MaterialTheme.typography.titleSmall,
                color = Color.Black
            )

            Text(
                buildAnnotatedString {
                    withStyle(
                        style = JostTypography.titleSmall.toSpanStyle()
                            .copy(color = OceanBlue, fontWeight = FontWeight.Bold)
                    ) {
                        append(displayPrice)
                    }
                    withStyle(
                        style = JostTypography.titleSmall.toSpanStyle()
                            .copy(color = Color.Black)
                    ) {
                        append("/" + stringResource(id = R.string.night))
                    }
                }
            )
        }

        Spacer(modifier = Modifier.height(AppSpacing.Medium))

        RoomInfoItem(
            icon = Icons.Default.Bed,
            text1 = stringResource(id = R.string.number_of_bed),
            text2 = room.numberOfBeds.toString()
        )

        RoomInfoItem(
            icon = Icons.Default.BedroomParent,
            text1 = stringResource(id = R.string.bed_type),
            text2 = room.bedType
        )

        RoomInfoItem(
            icon = Icons.Default.PeopleAlt,
            text1 = stringResource(id = R.string.capacity),
            text2 = room.capacity.toString() + " " + stringResource(id = R.string.people)
        )

        RoomInfoItem(
            icon = Icons.Default.Bathroom,
            text1 = stringResource(id = R.string.bath_room_type),
            text2 = room.bathroomType
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            RoomInfoItem(
                icon = Icons.Filled.AspectRatio,
                text1 = stringResource(id = R.string.room_size),
                text2 = "${room.roomSize} m²"
            )

            Box(
                modifier = Modifier
                    .padding(horizontal = Dimens.PaddingSM)
                    .width(1.dp)
                    .height(Dimens.HeightXXS)
                    .background(Color.LightGray)
            )

            RoomInfoItem(
                icon = Icons.Filled.Layers,
                text1 = stringResource(id = R.string.floor),
                text2 = room.floor.toString(),
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = Dimens.PaddingXS, horizontal = Dimens.PaddingXS),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                if (room.smokingPolicy) Icons.Default.SmokingRooms else Icons.Default.SmokeFree,
                contentDescription = null,
                tint = if (room.smokingPolicy) Color.Black else Color.Red,
                modifier = Modifier.size(Dimens.SizeSM)
            )

            Spacer(modifier = Modifier.width(AppSpacing.Medium))

            Text(
                text = if (room.smokingPolicy) stringResource(id = R.string.smoking_allowed) else stringResource(
                    id = R.string.no_smoking
                ),
                style = JostTypography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                color = if (room.smokingPolicy) Color.Black else Color.Red
            )
        }

        Spacer(modifier = Modifier.height(AppSpacing.Medium))

        Column {
            TitleSection(
                text1 = stringResource(id = R.string.description),
                text2 = ""
            )

            Spacer(modifier = Modifier.height(AppSpacing.Small))

            ReadMoreText(description = room.description, maxLine = 3)
        }

        Spacer(modifier = Modifier.height(AppSpacing.Medium))

        Column {
            TitleSection(
                text1 = stringResource(id = R.string.amenities),
                text2 = ""
            )

            Spacer(modifier = Modifier.height(AppSpacing.Small))

            AmenityItem(amenities = room.amenities)

            room.amenities.forEach { item ->
                Log.d("Amenities", "${item.name}, ${item.iconUrl}")
            }
        }

        AppLineGray()

        Spacer(modifier = Modifier.height(AppSpacing.Medium))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Default.Pets,
                contentDescription = null,
                tint = if (room.petPolicy) Green500 else Color.Red,
                modifier = Modifier.size(Dimens.SizeM)
            )

            Spacer(modifier = Modifier.width(AppSpacing.Medium))

            Text(
                text = if (room.petPolicy) stringResource(id = R.string.pets_allowed) else stringResource(
                    id = R.string.no_pets
                ),
                color = if (room.petPolicy) Green500 else Color.Red,
                style = JostTypography.titleSmall
            )
        }

        Text(
            text = stringResource(id = R.string.pet_policy),
            style = JostTypography.bodyMedium,
            color = OceanBlue,
            textDecoration = TextDecoration.Underline,
            modifier = Modifier.clickable {
                val intent = Intent(context, PetsPolicyActivity::class.java)
                intent.putExtra("isAllowed", room.petPolicy)
                context.startActivity(intent)
            }
        )
    }
}

@Composable
fun RoomInfoItem(
    icon: ImageVector,
    text1: String,
    text2: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .padding(vertical = Dimens.PaddingXS, horizontal = Dimens.PaddingXS),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            icon,
            contentDescription = null,
            tint = Color.Black.copy(alpha = 0.5f),
            modifier = Modifier.size(Dimens.SizeSM)
        )

        Spacer(modifier = Modifier.width(AppSpacing.Medium))

        Text(
            text = "$text1: ",
            style = JostTypography.bodyMedium,
            color = Color.Black.copy(alpha = 0.6f)
        )

        Text(
            text = text2,
            style = JostTypography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
            color = Color.Black
        )
    }
}

@Composable
fun AmenityItem(amenities: List<Amenity>) {
    Column(
        modifier = Modifier.fillMaxWidth(),
    ) {
        amenities.forEach { amenity ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(start = Dimens.PaddingXS)
            ) {
                AsyncImage(
                    model = amenity.iconUrl,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(Dimens.SizeML - 2.dp)
                )

                Spacer(modifier = Modifier.width(AppSpacing.Medium))

                Text(
                    text = amenity.name,
                    style = JostTypography.bodyMedium.copy(fontWeight = FontWeight.Medium),
                    color = Color.Black
                )
            }

            Spacer(modifier = Modifier.height(AppSpacing.MediumLarge))
        }
    }
}