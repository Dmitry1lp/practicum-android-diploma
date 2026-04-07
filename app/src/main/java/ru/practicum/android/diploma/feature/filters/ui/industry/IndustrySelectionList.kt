package ru.practicum.android.diploma.feature.filters.ui.industry

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import ru.practicum.android.diploma.app.ui.theme.AppDimensions
import ru.practicum.android.diploma.core.domain.model.FilterIndustry
import ru.practicum.android.diploma.feature.filters.ui.filters.RadioButtonItem

@Composable
fun IndustrySelectionList(
    industries: List<FilterIndustry>,
    selectedIndustry: FilterIndustry?,
    onSelectionChange: (FilterIndustry) -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val listState = rememberLazyListState()

    LaunchedEffect(listState.isScrollInProgress) {
        if (listState.isScrollInProgress) {
            keyboardController?.hide()
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = AppDimensions.paddingSmall),
        state = listState
    ) {
        items(
            items = industries,
            key = { it.id }
        ) { industry ->
            RadioButtonItem(
                text = industry.name,
                isSelected = industry == selectedIndustry
            ) {
                onSelectionChange(industry)
                keyboardController?.hide()
            }
        }
    }
}
