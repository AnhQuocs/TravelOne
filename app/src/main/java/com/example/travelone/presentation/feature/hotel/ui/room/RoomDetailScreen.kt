package com.example.travelone.presentation.feature.hotel.ui.room

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bed
import androidx.compose.material.icons.filled.BedroomParent
import androidx.compose.material.icons.filled.PeopleAlt
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.imageLoader
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.example.travelone.R
import com.example.travelone.domain.model.room.Room
import com.example.travelone.presentation.components.ReadMoreText
import com.example.travelone.presentation.components.TitleSection
import com.example.travelone.presentation.components.formatPrice
import com.example.travelone.ui.theme.AppShape
import com.example.travelone.ui.theme.AppSpacing
import com.example.travelone.ui.theme.Dimens
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
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.White)
                .sharedBounds(
                    rememberSharedContentState(key = selectedRoom.id),
                    animatedVisibilityScope = animatedVisibilityScope
                )
        ) {
            item {
                Column(
                    verticalArrangement = Arrangement.spacedBy(Dimens.PaddingS),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(context)
                            .data(selectedRoom.imageUrl)
                            .crossfade(true)
                            .memoryCachePolicy(CachePolicy.ENABLED)
                            .diskCachePolicy(CachePolicy.ENABLED)
                            .build(),
                        imageLoader = imageLoader,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(Dimens.HeightXXL + 20.dp)
                            .clip(RoundedCornerShape(AppShape.MediumShape))
                    )
                }
            }

            item {
                RoomInfo(selectedRoom)
            }
        }
    }
}

@Composable
fun RoomInfo(
    room: Room
) {
    val displayPrice = formatPrice(room.pricePerNight)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(Dimens.PaddingSM)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = Dimens.PaddingS),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = room.roomType,
                style = MaterialTheme.typography.titleSmall,
                color = Color.Black
            )

            Text(
                buildAnnotatedString {
                    withStyle(style = JostTypography.titleSmall.toSpanStyle().copy(color = OceanBlue, fontWeight = FontWeight.Bold)) {
                        append(displayPrice)
                    }
                    withStyle(style = JostTypography.titleSmall.toSpanStyle().copy(color = Color.Black)) {
                        append("/" + stringResource(id = R.string.night))
                    }
                },
                modifier = Modifier.padding(start = Dimens.PaddingXS)
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

        Spacer(modifier = Modifier.height(AppSpacing.Medium))

        Column {
            TitleSection(
                text1 = stringResource(id = R.string.description),
                text2 = ""
            )

            Spacer(modifier = Modifier.height(AppSpacing.Small))

            ReadMoreText(description = room.description, maxLine = 3)
        }
    }
}

@Composable
fun RoomInfoItem(
    icon: ImageVector,
    text1: String,
    text2: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
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