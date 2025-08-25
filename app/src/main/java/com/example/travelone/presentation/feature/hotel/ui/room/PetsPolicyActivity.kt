package com.example.travelone.presentation.feature.hotel.ui.room

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.travelone.BaseComponentActivity
import com.example.travelone.R
import com.example.travelone.presentation.components.AppLineGray
import com.example.travelone.ui.theme.AppSpacing
import com.example.travelone.ui.theme.Dimens
import com.example.travelone.ui.theme.Green500
import com.example.travelone.ui.theme.JostTypography

class PetsPolicyActivity : BaseComponentActivity() {
    private var isAllowed: Boolean = true
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        isAllowed = intent.getBooleanExtra("isAllowed", true)

        setContent {
            PetsPolicyScreen(
                onBack = { finish() },
                isAllowed = isAllowed
            )
        }
    }
}

@Composable
fun PetsPolicyScreen(
    onBack: () -> Unit = {},
    isAllowed: Boolean = true
) {
    Scaffold(
        topBar = {
            Column {
                Box(
                    modifier = Modifier.height(Dimens.HeightML),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(
                            onClick = onBack
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_back),
                                contentDescription = null,
                                colorFilter = ColorFilter.tint(Color.Black),
                                modifier = Modifier.size(Dimens.SizeML)
                            )
                        }

                        Text(
                            text = stringResource(id = R.string.pet_policy_title),
                            style = JostTypography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                            color = Color.Black,
                            modifier = Modifier.padding(start = Dimens.PaddingS)
                        )
                    }
                }

                AppLineGray()
            }
        }
    ) { paddingValues ->

        val color = if(isAllowed) Green500 else Color.Red

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(paddingValues)
                .padding(Dimens.PaddingM)
        ) {
            if(isAllowed) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.Pets,
                        contentDescription = null,
                        tint = color,
                        modifier = Modifier.size(Dimens.SizeM)
                    )

                    Spacer(modifier = Modifier.width(AppSpacing.Medium))

                    Text(
                        text = stringResource(id = R.string.pet_policy_allowed),
                        style = JostTypography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                        color = color
                    )
                }

                Text(
                    text = stringResource(id = R.string.pet_policy_intro_allowed),
                    style = JostTypography.bodyMedium,
                    color = Color.Black,
                    lineHeight = 20.sp,
                    modifier = Modifier.padding(vertical = Dimens.PaddingXS)
                )

                PolicyItem(
                    title = stringResource(id = R.string.pet_policy_conditions_title),
                    text1 = stringResource(id = R.string.pet_policy_condition_1),
                    text2 = stringResource(id = R.string.pet_policy_condition_2),
                    text3 = stringResource(id = R.string.pet_policy_condition_3)
                )

                PolicyItem(
                    title = stringResource(id = R.string.pet_policy_fees_title),
                    text1 = stringResource(id = R.string.pet_policy_fee_1),
                    text2 = stringResource(id = R.string.pet_policy_fee_2)
                )

                PolicyItem(
                    title = stringResource(id = R.string.pet_policy_restrictions_title),
                    text1 = stringResource(id = R.string.pet_policy_restriction_1),
                    text2 = stringResource(id = R.string.pet_policy_restriction_2),
                    text3 = stringResource(id = R.string.pet_policy_restriction_3)
                )

                PolicyItem(
                    title = stringResource(id = R.string.pet_policy_responsibility_title),
                    text1 = stringResource(id = R.string.pet_policy_responsibility_1),
                    text2 = stringResource(id = R.string.pet_policy_responsibility_2)
                )
            } else {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.Pets,
                        contentDescription = null,
                        tint = color,
                        modifier = Modifier.size(Dimens.SizeM)
                    )

                    Spacer(modifier = Modifier.width(AppSpacing.Medium))

                    Text(
                        text = stringResource(id = R.string.pet_policy_not_allowed),
                        style = JostTypography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                        color = color
                    )
                }

                Text(
                    text = stringResource(id = R.string.pet_policy_intro_not_allowed),
                    style = JostTypography.bodyMedium,
                    color = Color.Black,
                    lineHeight = 20.sp,
                    modifier = Modifier.padding(vertical = Dimens.PaddingXS)
                )
            }
        }
    }
}

@Composable
fun PolicyItem(
    title: String,
    text1: String,
    text2: String,
    text3: String = "",
) {
    Text(
        text = "$title:",
        style = JostTypography.titleSmall.copy(fontWeight = FontWeight.SemiBold),
    )

    Column(
        modifier = Modifier.padding(start = Dimens.PaddingS).padding(bottom = Dimens.PaddingS)
    ) {
        Text(
            text = "1. $text1",
            style = JostTypography.bodyMedium,
            lineHeight = 20.sp
        )

        Text(
            text = "2. $text2",
            style = JostTypography.bodyMedium,
            lineHeight = 20.sp
        )

        if(text3 != "") {
            Text(
                text = "3. $text3",
                style = JostTypography.bodyMedium,
                lineHeight = 20.sp
            )
        }
    }
}