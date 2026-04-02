package ru.practicum.android.diploma.core.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import ru.practicum.android.diploma.app.ui.theme.AppDimensions
import ru.practicum.android.diploma.core.utils.antiRepetitionClick

/**
 * @see [ru.practicum.android.diploma.feature.filters.ui.HintedFilterItem]
 * @see [ru.practicum.android.diploma.feature.filters.ui.InactiveFilterItem]
 * @see [ru.practicum.android.diploma.feature.filters.ui.SwitchFilterItem]
 */
@Composable
fun LabelActionListItem(
    leadingContent: @Composable () -> Unit,
    trailingContent: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null
) {
    Row(
        modifier = modifier
            .antiRepetitionClick { onClick?.invoke() }
            .background(MaterialTheme.colorScheme.background)
            .height(AppDimensions.LabelActionListItem.itemHeight)
            .fillMaxWidth()
            .padding(AppDimensions.LabelActionListItem.contentPadding),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        leadingContent()
        Box(
            modifier = Modifier
                .size(AppDimensions.LabelActionListItem.trailingContentSize)
                .padding(AppDimensions.LabelActionListItem.trailingContentPadding),
            contentAlignment = Alignment.Center
        ) {
            trailingContent()
        }
    }
}
