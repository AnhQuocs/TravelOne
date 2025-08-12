package com.example.travelone.presentation.feature.search_history.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.travelone.R
import com.example.travelone.presentation.components.TitleSection
import com.example.travelone.presentation.feature.search.viewmodel.UnifiedSearchViewModel
import com.example.travelone.presentation.feature.search_history.viewmodel.SearchHistoryViewModel
import com.example.travelone.ui.theme.AppSpacing
import com.example.travelone.ui.theme.CrimsonRed
import com.example.travelone.ui.theme.Dimens
import com.example.travelone.ui.theme.JostTypography

@Composable
fun SearchHistoryList(
    searchHistoryViewModel: SearchHistoryViewModel = hiltViewModel(),
    unifiedSearchViewModel: UnifiedSearchViewModel = hiltViewModel()
) {
    val historyList = searchHistoryViewModel.historyList
    val isLoading = searchHistoryViewModel.isClearing

    val itemHeight = Dimens.HeightDefault
    val visibleItemCount = historyList.take(3).size
    val boxHeight = if (visibleItemCount > 0) itemHeight * visibleItemCount else itemHeight * 3

    Column(modifier = Modifier.fillMaxWidth()) {
        TitleSection(
            text1 = stringResource(id = R.string.recent_search),
            text2 = stringResource(id = R.string.clear_all),
            onClick = { searchHistoryViewModel.clearAllHistory() },
            color = CrimsonRed
        )

        Spacer(modifier = Modifier.height(AppSpacing.Medium))

        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(boxHeight),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    color = Color.Gray.copy(alpha = 0.6f)
                )
            }
        } else {
            if (historyList.isNotEmpty()) {
                historyList.take(3).forEach { item ->
                    SearchHistoryItem(
                        title = item.title,
                        subTitle = item.subTitle,
                        onHistoryClick = {
                            unifiedSearchViewModel.onSuggestionClicked(item.title)
                        }
                    )
                }
            } else {
                Text(
                    text = stringResource(id = R.string.no_recent_searches),
                    color = Color.Black.copy(alpha = 0.6f),
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(AppSpacing.ExtraLarge))
            }
        }
    }
}

@Composable
fun SearchHistoryItem(
    title: String,
    subTitle: String,
    onHistoryClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = Dimens.PaddingS)
            .clickable { onHistoryClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            Icons.Default.History,
            contentDescription = null,
            tint = Color.Black.copy(alpha = 0.6f),
            modifier = Modifier.size(Dimens.SizeM)
        )

        Spacer(modifier = Modifier.width(AppSpacing.MediumLarge))

        Column(
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = title,
                style = JostTypography.bodyMedium,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(AppSpacing.Small))

            Text(
                text = subTitle,
                style = JostTypography.labelLarge,
                color = Color.Black.copy(alpha = 0.6f)
            )
        }
    }
}