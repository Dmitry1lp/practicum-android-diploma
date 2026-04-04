package ru.practicum.android.diploma.feature.filters.ui.region

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import ru.practicum.android.diploma.core.domain.model.GeoArea
import ru.practicum.android.diploma.feature.filters.presentation.region.SelectRegionUiState

class SelectRegionUiStateProvider : PreviewParameterProvider<SelectRegionUiState> {

    private val regions = listOf(
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

    override val values: Sequence<SelectRegionUiState>
        get() = sequenceOf(
            SelectRegionUiState.FetchError,
            SelectRegionUiState.NoSuchRegion,
            SelectRegionUiState.Content(
                regions.mapIndexed { index, name ->
                    GeoArea.Region(
                        id = index,
                        name = name,
                        countryId = index
                    )
                }
            )
        )
}
