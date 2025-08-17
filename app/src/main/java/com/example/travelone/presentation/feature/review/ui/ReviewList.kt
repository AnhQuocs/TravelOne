package com.example.travelone.presentation.feature.review.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.travelone.R
import com.example.travelone.domain.model.review.Review
import com.example.travelone.presentation.components.TitleSection
import com.example.travelone.presentation.feature.review.viewmodel.ReviewViewModel
import com.example.travelone.ui.theme.AppSpacing
import com.example.travelone.ui.theme.Dimens
import com.example.travelone.ui.theme.JostTypography
import com.example.travelone.ui.theme.TravelOneTheme

@Composable
fun ReviewList(reviews: List<Review>) {
    Column {
        TitleSection(
            text1 = stringResource(id = R.string.reviews),
            text2 = stringResource(id = R.string.see_all),
            onClick = {  }
        )

        reviews.take(4).forEach { review ->
            ReviewItem(review)
        }
    }
}

@Composable
fun ReviewItem(review: Review) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = Dimens.PaddingS),
        verticalAlignment = Alignment.Top
    ) {
        AsyncImage(
            model = review.userProfilePicture,
            contentDescription = null,
            placeholder = painterResource(R.drawable.user_review),
            error = painterResource(R.drawable.user_review),
            modifier = Modifier
                .size(Dimens.SizeXXL)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.width(AppSpacing.MediumLarge))

        Column(
            modifier = Modifier
                .weight(1f)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(end = Dimens.PaddingXS),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = review.userName,
                    style = JostTypography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                    color = Color.Black
                )

                Text(
                    text = "⭐" + review.rating,
                    color = Color.Black,
                    style = JostTypography.labelLarge.copy(fontWeight = FontWeight.SemiBold)
                )
            }

            Spacer(modifier = Modifier.height(AppSpacing.Small))

            Text(
                text = review.comment,
                style = JostTypography.labelLarge.copy(fontWeight = FontWeight.Normal),
                color = Color.Black.copy(alpha = 0.6f),
                modifier = Modifier
                    .padding(end = Dimens.PaddingM)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ReviewItemPreview() {
    val review = Review (
        userId = "user123",
        userName = "AnhQuocs",
        userProfilePicture = "",
        serviceId = "hotel_001",
        serviceType = "hotel",
        rating = 5,
        comment = "My family and I had a wonderful stay here. The room was spotless and the ocean view was breathtaking!",
        timestamp = ""
    )

    TravelOneTheme {
        ReviewItem(review)
    }
}