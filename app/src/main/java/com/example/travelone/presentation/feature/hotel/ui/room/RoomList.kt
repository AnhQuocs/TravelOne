package com.example.travelone.presentation.feature.hotel.ui.room

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.example.travelone.R
import com.example.travelone.domain.model.room.Room
import com.example.travelone.presentation.components.TitleSection
import com.example.travelone.ui.theme.AppShape
import com.example.travelone.ui.theme.AppSpacing
import com.example.travelone.ui.theme.Dimens
import com.example.travelone.ui.theme.JostTypography

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun RoomList(
    rooms: List<Room>,
    onRoomClick: (Room) -> Unit,
    transitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope
) {
    if (rooms.isEmpty()) return
    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        TitleSection(
            text1 = stringResource(id = R.string.rooms),
            text2 = ""
        )

        Spacer(modifier = Modifier.height(AppSpacing.Medium))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(Dimens.HeightXL4 - 20.dp),
            horizontalArrangement = Arrangement.spacedBy(Dimens.PaddingS)
        ) {
            with(transitionScope) {
                Box(
                    modifier = Modifier
                        .weight(2f)
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(AppShape.LargeShape))
                        .sharedBounds(
                            rememberSharedContentState(key = rooms[0].id),
                            animatedVisibilityScope = animatedVisibilityScope
                        )
                        .clickable { onRoomClick(rooms[0]) }
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(context)
                            .data(rooms[0].imageUrl)
                            .crossfade(true)
                            .diskCachePolicy(CachePolicy.ENABLED)
                            .memoryCachePolicy(CachePolicy.ENABLED)
                            .build(),
                        contentDescription = null,
                        placeholder = painterResource(R.drawable.placeholder),
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )

                    Text(
                        text = rooms[0].roomType,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Bold,
                            shadow = Shadow(
                                color = Color.Black,
                                offset = Offset(2f, 2f),
                                blurRadius = 4f
                            )
                        ),
                        color = Color.White,
                        modifier = Modifier.padding(Dimens.PaddingXSPlus).align(Alignment.BottomStart)
                    )
                }
            }

            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.spacedBy(Dimens.PaddingS)
            ) {
                rooms.drop(1).take(3).forEach { room ->
                    with(transitionScope) {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(AppShape.MediumShape))
                                .sharedBounds(
                                    rememberSharedContentState(key = room.id),
                                    animatedVisibilityScope = animatedVisibilityScope
                                )
                                .clickable { onRoomClick(room) }
                        ) {
                            AsyncImage(
                                model = ImageRequest.Builder(context)
                                    .data(room.imageUrl)
                                    .crossfade(true)
                                    .diskCachePolicy(CachePolicy.ENABLED)
                                    .memoryCachePolicy(CachePolicy.ENABLED)
                                    .build(),
                                contentDescription = null,
                                placeholder = painterResource(R.drawable.placeholder),
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize()
                            )

                            Text(
                                text = room.roomType,
                                color = Color.White,
                                style = JostTypography.bodySmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    lineHeight = 12.sp,
                                    shadow = Shadow(
                                        color = Color.Black,
                                        offset = Offset(2f, 2f),
                                        blurRadius = 4f
                                    )
                                ),
                                modifier = Modifier
                                    .align(Alignment.BottomStart)
                                    .padding(Dimens.PaddingXS)
                            )
                        }
                    }
                }
            }
        }
    }
}