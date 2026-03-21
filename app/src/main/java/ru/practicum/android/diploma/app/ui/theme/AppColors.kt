package ru.practicum.android.diploma.app.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

data class AppColors(
    val switchCheckedThumb: Color,
    val switchCheckedTrack: Color,
    val switchUncheckedThumb: Color,
    val switchUncheckedTrack: Color,
    val settingDrawable: Color,

    val actionButtonContainer: Color,
    val actionButtonContent: Color,

    val searchBarBackground: Color,
    val searchBarIcon: Color,
    val searchBarText: Color,
    val searchBarHint: Color
)

val LocalAppColors = staticCompositionLocalOf<AppColors> {
    error("No colors provided")
}

val LightAppColors = AppColors(
    switchCheckedThumb = Blue,
    switchCheckedTrack = SoftBlue,
    switchUncheckedThumb = Gray,
    switchUncheckedTrack = LightGray,
    settingDrawable = Gray,

    actionButtonContainer = Black,
    actionButtonContent = White,

    searchBarBackground = LightGray,
    searchBarIcon = Gray,
    searchBarText = Black,
    searchBarHint = Gray
)

val DarkAppColors = AppColors(
    switchCheckedThumb = Blue,
    switchCheckedTrack = SoftBlue,
    switchUncheckedThumb = Gray,
    switchUncheckedTrack = LightGray,
    settingDrawable = White,

    actionButtonContainer = White,
    actionButtonContent = Black,

    searchBarBackground = DarkGrayTertiary,
    searchBarIcon = White,
    searchBarText = White,
    searchBarHint = Gray
)
