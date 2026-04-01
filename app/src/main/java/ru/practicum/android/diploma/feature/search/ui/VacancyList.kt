package ru.practicum.android.diploma.feature.search.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.practicum.android.diploma.core.domain.model.Vacancy
import ru.practicum.android.diploma.core.presentation.components.VacancyItem
import ru.practicum.android.diploma.core.presentation.model.toItemData

@Composable
fun VacancyList(
    vacancies: List<Vacancy>,
    modifier: Modifier = Modifier,
    onLoadNextPage: () -> Unit,
    isLoadingNextPage: Boolean,
    onVacancyClick: (Vacancy) -> Unit
) {
    val listState = rememberLazyListState()

    val shouldLoadNext = remember {
        derivedStateOf {
            val lastVisibleItem = listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
            val totalItems = listState.layoutInfo.totalItemsCount
            lastVisibleItem != null && lastVisibleItem >= totalItems - 1
        }
    }

    LaunchedEffect(shouldLoadNext.value) {
        if (shouldLoadNext.value) {
            onLoadNextPage()
        }
    }

    LazyColumn(
        state = listState,
        modifier = modifier,
        contentPadding = PaddingValues(top = 38.dp)
    ) {
        items(
            items = vacancies,
            key = { it.id }
        ) { vacancy ->
            VacancyItem(
                data = vacancy.toItemData(),
                modifier = Modifier
                    .fillMaxWidth(),
                onClick = { onVacancyClick(vacancy) }
            )
        }
        if (isLoadingNextPage) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}
