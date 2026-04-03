package ru.practicum.android.diploma.feature.filters.ui.country

import androidx.compose.ui.tooling.preview.PreviewParameterProvider

class SelectCountryUiStateProvider : PreviewParameterProvider<List<String>?> {

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

    override val values: Sequence<List<String>?>
        get() = sequenceOf(
            null,
            countries
        )

}
