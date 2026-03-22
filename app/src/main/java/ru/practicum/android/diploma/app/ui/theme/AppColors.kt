package ru.practicum.android.diploma.app.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

data class AppColors(

    val iconColor: Color, // иконки в фиче Вакансии

    val primaryButtonContainer: Color,
    val primaryButtonContent: Color,

    val tertiaryButtonContainer: Color,
    val tertiaryButtonContent: Color,

    val searchBarBackground: Color,
    val searchBarIcon: Color,
    val searchBarText: Color,
    val searchBarHint: Color
)

val LocalAppColors = staticCompositionLocalOf<AppColors> {
    error("No colors provided")
}

val LightAppColors = AppColors(
    iconColor = Black,

    primaryButtonContainer = Blue,
    primaryButtonContent = White,

    tertiaryButtonContainer = White,
    tertiaryButtonContent = Red,

    searchBarBackground = LightGray,
    searchBarIcon = Black,
    searchBarText = Black,
    searchBarHint = LightGray
)

val DarkAppColors = AppColors(
    iconColor = White,

    primaryButtonContainer = Blue,
    primaryButtonContent = White,

    tertiaryButtonContainer = Black,
    tertiaryButtonContent = Red,

    searchBarBackground = Gray,
    searchBarIcon = Black,
    searchBarText = Black,
    searchBarHint = White
)
