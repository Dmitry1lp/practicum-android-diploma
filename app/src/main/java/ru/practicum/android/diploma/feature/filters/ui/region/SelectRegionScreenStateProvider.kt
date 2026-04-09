package ru.practicum.android.diploma.feature.filters.ui.region

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import ru.practicum.android.diploma.core.domain.model.GeoArea
import ru.practicum.android.diploma.feature.filters.presentation.worklocation.AreasStatus
import ru.practicum.android.diploma.feature.filters.presentation.worklocation.WorkLocationScreenState

class SelectRegionScreenStateProvider : PreviewParameterProvider<WorkLocationScreenState> {

    private val countriesStr = listOf(
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

    private val regionsStr = listOf(
        "Москва",
        "Санкт-Петербург",
        "Московская область",
        "Ленинградская область",
        "Республика Татарстан",
        "Республика Башкортостан",
        "Свердловская область",
        "Краснодарский край",
        "Новосибирская область",
        "Нижегородская область"
    )

    val countries = countriesStr.mapIndexed { index, name ->
        GeoArea.Country(
            id = index,
            name = name,
            regions = if (index == 0) {
                regionsStr.mapIndexed { index, name ->
                    GeoArea.Region(
                        id = index,
                        name = name,
                        countryId = index
                    )
                }
            } else {
                listOf()
            }
        )
    }

    private val initScreenState = WorkLocationScreenState(
        status = AreasStatus.Content,
        countries = countries
    )

    override val values: Sequence<WorkLocationScreenState>
        get() = sequenceOf(
            initScreenState.copy(status = AreasStatus.Loading),
            initScreenState.copy(status = AreasStatus.FetchError),
            initScreenState.copy(status = AreasStatus.Content),
            initScreenState.copy(status = AreasStatus.Content, regionSearchQuery = "Медвежьи-Озёра")
        )

}
