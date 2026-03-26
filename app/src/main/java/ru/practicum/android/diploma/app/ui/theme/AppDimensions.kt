package ru.practicum.android.diploma.app.ui.theme

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp

object AppDimensions {

    val paddingBig = 24.dp
    val paddingMedium = 16.dp
    val paddingSmall = 8.dp

    val buttonContentPadding = PaddingValues(vertical = 20.dp, horizontal = 8.dp)

    val chipCornerRadius = 12.dp
    val chipContentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp)

    val rowListItemHeight = 60.dp
    val rowListItemTrailingContentPadding = 4.dp
    val rowListItemContentPadding = PaddingValues(top = 6.dp, bottom = 6.dp, start = 16.dp)
    val rowListItemTrailingContentSize = DpSize(48.dp, 48.dp)

    val imageRowListItemImageSize = DpSize(48.dp, 48.dp)
    val imageRowListItemImageCornerRadius = 12.dp

    val vacancyListItemContentPadding = PaddingValues(vertical = 9.dp, horizontal = 16.dp)
    val vacancyListItemImageBorderWidth = 1.dp
    val vacancyListItemGap = 12.dp

    val loadingIndicatorSize = DpSize(48.dp, 48.dp)
    val searchBarIconSize = DpSize(24.dp, 24.dp)

}
