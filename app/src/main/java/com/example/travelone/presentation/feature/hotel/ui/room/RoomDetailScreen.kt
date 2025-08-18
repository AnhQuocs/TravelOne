package com.example.travelone.presentation.feature.hotel.ui.room

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.example.travelone.R
import com.example.travelone.domain.model.room.Room

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
    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(context)
            .data(selectedRoom.imageUrl)
            .crossfade(true)
            .diskCachePolicy(CachePolicy.ENABLED)
            .memoryCachePolicy(CachePolicy.ENABLED)
            .build(),
        placeholder = painterResource(R.drawable.placeholder)
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        item {
            with(transitionScope) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .sharedBounds(
                            rememberSharedContentState(key = selectedRoom.id),
                            animatedVisibilityScope = animatedVisibilityScope
                        )
                ) {
                    Image(
                        painter = painter,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Text(
                        text = selectedRoom.roomType
                    )

                    Text(
                        text = selectedRoom.description
                    )

                    Text(
                        text = selectedRoom.bedType
                    )

                    Text(
                        text = selectedRoom.capacity.toString()
                    )

                    Text(
                        text = selectedRoom.pricePerNight.toString()
                    )
                }
            }
        }
    }
}