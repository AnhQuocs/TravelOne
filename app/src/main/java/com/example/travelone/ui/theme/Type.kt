package com.example.travelone.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.travelone.R

// Set of Material typography styles to start with

val JostFontFamily = FontFamily(
    Font(R.font.jost_font),
//    Font(R.font.jost_italic_font, FontWeight.SemiBold),
)

val JostTypography = Typography(
    titleMedium = TextStyle(
        fontFamily = JostFontFamily,
        fontSize = 20.sp
    ),
    titleSmall = TextStyle(
        fontFamily = JostFontFamily,
        fontSize = 18.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = JostFontFamily,
        fontSize = 16.sp
    )
)

val Typography = Typography(

    bodyLarge = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 18.sp
    ),
    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */

    bodyMedium = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.Normal
    ),
    titleSmall = TextStyle(
        fontSize = 20.sp,
        fontWeight = FontWeight.SemiBold
    ),
    titleLarge = TextStyle(
        fontSize = 22.sp,
        fontWeight = FontWeight.SemiBold
    ),
    titleMedium = TextStyle (
        fontSize = 16.sp,
        fontWeight = FontWeight.SemiBold
    ),
    displayMedium = TextStyle(
        fontSize = 44.sp,
        fontWeight = FontWeight.SemiBold
    ),
    headlineMedium = TextStyle(
        fontSize = 28.sp,
        fontWeight = FontWeight.SemiBold
    )
)