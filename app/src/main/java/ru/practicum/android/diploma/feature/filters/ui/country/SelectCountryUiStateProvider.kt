package ru.practicum.android.diploma.feature.filters.ui.country

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import ru.practicum.android.diploma.core.domain.model.GeoArea
import ru.practicum.android.diploma.feature.filters.presentation.country.CountryUiState

class SelectCountryUiStateProvider : PreviewParameterProvider<CountryUiState> {

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

    override val values: Sequence<CountryUiState>
        get() = sequenceOf(
            CountryUiState.FetchError,
            CountryUiState.Content(
                countries.mapIndexed { index, name ->
                    GeoArea.Country(index, name, emptyList())
                }
            )
        )

}
