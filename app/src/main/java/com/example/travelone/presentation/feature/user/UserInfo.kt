package com.example.travelone.presentation.feature.user

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.travelone.R
import com.example.travelone.domain.model.auth.User
import com.example.travelone.domain.model.weather.Weather
import com.example.travelone.domain.model.weather.WeatherResult
import com.example.travelone.ui.theme.AppShape
import com.example.travelone.ui.theme.AppSpacing
import com.example.travelone.ui.theme.Dimens
import com.example.travelone.ui.theme.JostTypography

@Composable
fun UserInfo(
    user: User,
    weather: WeatherResult,
    onSearch: () -> Unit,
    onOpenNotification: () -> Unit
) {
    Row(
        modifier = Modifier
            .background(color = Color.White)
            .padding(horizontal = Dimens.PaddingM)
            .padding(bottom = Dimens.PaddingXXS)
            .fillMaxWidth()
            .height(Dimens.HeightXL),
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
                    .size(Dimens.SizeXXLPlus)
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
                    modifier = Modifier.padding(start = Dimens.PaddingXS)
                )

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_location),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(color = Color.Black.copy(alpha = 0.4f)),
                        modifier = Modifier.size(Dimens.SizeM)
                    )

                    Spacer(modifier = Modifier.width(AppSpacing.Small))

                    Text(
                        text = weather.name.toString(),
                        color = Color.Black.copy(alpha = 0.4f),
                        style = JostTypography.titleSmall.copy(fontWeight = FontWeight.Medium)
                    )
                }
            }
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = Dimens.PaddingS)
        ) {
            Box(
                modifier = Modifier
                    .size(Dimens.SizeXLPlus)
                    .border(1.dp, color = Color.LightGray, shape = CircleShape)
                    .clickable {  },
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_search),
                    contentDescription = null,
                    modifier = Modifier.size(Dimens.SizeM)
                )
            }

            Spacer(modifier = Modifier.width(AppSpacing.Medium))

            Box(
                modifier = Modifier
                    .size(Dimens.SizeXLPlus)
                    .border(1.dp, color = Color.LightGray, shape = CircleShape)
                    .clickable {  },
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_notification),
                    contentDescription = null,
                    modifier = Modifier.size(Dimens.SizeML)
                )
            }
        }
    }
}