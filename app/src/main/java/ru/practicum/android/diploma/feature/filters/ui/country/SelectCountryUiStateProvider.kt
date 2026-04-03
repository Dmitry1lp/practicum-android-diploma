package ru.practicum.android.diploma.feature.filters.ui.country

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import ru.practicum.android.diploma.core.domain.model.GeoArea
import ru.practicum.android.diploma.feature.filters.presentation.country.SelectCountryUiState

class SelectCountryUiStateProvider : PreviewParameterProvider<SelectCountryUiState> {

    private val countries = listOf(
        "Россия",
        "США",
        "Китай",
        "Индия",
        "Бразилия",
        "Германия",
        "Великобритания",
        "Франция",
        "Италия",
        "Канада",
        "Япония",
        "Австралия",
        "Южная Корея",
        "Испания",
        "Мексика",
        "Индонезия",
        "Турция",
        "Саудовская Аравия",
        "Нидерланды",
        "Швейцария"
    )

    override val values: Sequence<SelectCountryUiState>
        get() = sequenceOf(
            SelectCountryUiState.FetchError,
            SelectCountryUiState.Content(
                countries.mapIndexed { index, name ->
                    GeoArea.Country(
                        index,
                        name,
                        emptyList()
                    )
                }
            )
        )

}
