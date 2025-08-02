package com.example.travelone.presentation.feature.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.travelone.R
import com.example.travelone.domain.model.auth.User
import com.example.travelone.ui.theme.AppShape
import com.example.travelone.ui.theme.AppSpacing
import com.example.travelone.ui.theme.Dimens
import com.example.travelone.ui.theme.JostTypography
import com.example.travelone.ui.theme.TravelOneTheme

@Composable
fun UserInfo(
    user: User,
    onSearch: () -> Unit,
    onOpenNotification: () -> Unit
) {
    Row(
        modifier = Modifier
            .background(color = Color.White)
            .padding(horizontal = Dimens.MediumPadding)
            .padding(bottom = Dimens.MediumPadding)
            .fillMaxWidth()
            .height(Dimens.ExtraLargeHeight),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Bottom
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.user_avatar),
                contentDescription = null,
                modifier = Modifier
                    .size(Dimens.LargeSize)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(AppShape.MediumShape))

            Column(
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = user.username ?: "User",
                    color = Color.Black,
                    style = JostTypography.titleMedium,
                    modifier = Modifier.padding(start = Dimens.MicroPadding)
                )

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_location),
                        contentDescription = null,
                        modifier = Modifier.size(Dimens.RegularSize)
                    )

                    Spacer(modifier = Modifier.width(AppSpacing.Small))

                    Text(
                        text = "Ha Dong", // debug
                        color = Color.Black.copy(alpha = 0.6f),
                        style = JostTypography.titleMedium.copy(fontWeight = FontWeight.Medium)
                    )
                }
            }
        }

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(Dimens.SemiLargeSize)
                    .border(1.dp, color = Color.LightGray, shape = CircleShape)
                    .clickable {  },
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_search),
                    contentDescription = null,
                    modifier = Modifier.size(Dimens.RegularSize)
                )
            }

            Spacer(modifier = Modifier.width(AppSpacing.Medium))

            Box(
                modifier = Modifier
                    .size(Dimens.SemiLargeSize)
                    .border(1.dp, color = Color.LightGray, shape = CircleShape)
                    .clickable {  },
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_notification),
                    contentDescription = null,
                    modifier = Modifier.size(Dimens.SemiMediumSize)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun UserInfoPreview() {
    val user = User (
        uid = "user123",
        username = "AnhQuocs",
        email = "anhquoc123@gmail.com"
    )

    TravelOneTheme {
        UserInfo(
            user = user,
            onSearch = {},
            onOpenNotification = {}
        )
    }
}