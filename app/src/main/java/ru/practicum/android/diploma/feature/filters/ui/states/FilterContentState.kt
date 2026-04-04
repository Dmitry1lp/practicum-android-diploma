package ru.practicum.android.diploma.feature.filters.ui.states

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.practicum.android.diploma.core.domain.model.GeoArea
import ru.practicum.android.diploma.feature.filters.ui.FilterItem

@Composable
fun FilterContentState(
    items: List<GeoArea>,
    onItemClick: (GeoArea) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        items(
            items = items,
            key = { it.id }
        ) { item ->
            FilterItem(
                text = item.name,
                onClick = { onItemClick(item) },
                isActive = true
            )
        }
    }
}
