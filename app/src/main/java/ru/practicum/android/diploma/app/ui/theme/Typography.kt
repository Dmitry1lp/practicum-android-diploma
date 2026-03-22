package ru.practicum.android.diploma.app.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import ru.practicum.android.diploma.R

/**
 * Typography mapping:
 * titleLarge - Bold / 32
 * titleMedium - Medium / 22
 * titleSmall - Medium / 16
 * bodyLarge - Regular / 16
 * labelMedium - Regular / 12
 * Note:
 * Bold отсутствует в ресурсах,
 * используется Medium.
 */

val YsDisplayFontFamily = FontFamily(
    Font(R.font.ys_display_regular, weight = FontWeight.Normal),
    Font(R.font.ys_display_medium, weight = FontWeight.Medium)
)

val AppTypography = Typography(

    // Bold / 32
    titleLarge = TextStyle(
        fontFamily = YsDisplayFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp,
        lineHeight = 38.sp
    ),

    // Medium / 22
    titleMedium = TextStyle(
        fontFamily = YsDisplayFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 22.sp,
        lineHeight = 26.sp
    ),

    // Medium / 16
    titleSmall = TextStyle(
        fontFamily = YsDisplayFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        lineHeight = 19.sp
    ),

    // Regular / 16
    bodyLarge = TextStyle(
        fontFamily = YsDisplayFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 19.sp
    ),

    // Regular / 12
    labelMedium = TextStyle(
        fontFamily = YsDisplayFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 16.sp
    )
)

