package ru.practicum.android.diploma.core.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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

@Composable
fun RowListItem(
    leadingContent: @Composable () -> Unit,
    trailingContent: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null
) {
    Row(
        modifier = modifier
            .clickable(enabled = onClick != null) {
                onClick?.invoke()
            }
            .background(MaterialTheme.colorScheme.background)
            .height(AppDimensions.rowListItemHeight)
            .fillMaxWidth()
            .padding(AppDimensions.rowListItemContentPadding),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        leadingContent()
        Box(
            modifier = Modifier
                .size(AppDimensions.rowListItemTrailingContentSize)
                .padding(AppDimensions.rowListItemTrailingContentPadding),
            contentAlignment = Alignment.Center
        ) {
            trailingContent()
        }
    }
}
