package ru.practicum.android.diploma.app.ui.theme

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp

object AppDimensions {

    val heightTopBar = 64.dp

    val paddingVeryBig = 32.dp
    val paddingBig = 24.dp
    val paddingMedium = 16.dp
    val paddingSmall = 8.dp
    val paddingVerySmall = 6.dp

    val teamScreenPadding = PaddingValues(top = paddingBig, start = paddingMedium, end = paddingMedium)

    val buttonContentPadding = PaddingValues(vertical = 20.dp, horizontal = 8.dp)
    object Button {
        val contentPadding = PaddingValues(vertical = 20.dp, horizontal = 8.dp)
    }

    object Chip {
        val cornerRadius = 12.dp
        val contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp)
    }

    object LabelActionListItem {
        val itemHeight = 60.dp
        val contentPadding = PaddingValues(top = 6.dp, bottom = 6.dp, start = 16.dp)
        val trailingContentSize = DpSize(48.dp, 48.dp)
        val trailingContentPadding = PaddingValues(4.dp)
    }

    object ThumbnailListItem {
        val imageSize = DpSize(48.dp, 48.dp)
        val imageCornerRadius = 12.dp
    }

    object VacancyItem {
        val contentPadding = PaddingValues(vertical = 9.dp, horizontal = 16.dp)
        val imageBorderWidth = 1.dp
        val contentGap = 12.dp
    }

    object StateInfo {
        val contentGap = 16.dp
    }

    val loadingIndicatorSize = DpSize(48.dp, 48.dp)
    val searchBarIconSize = DpSize(24.dp, 24.dp)

}
