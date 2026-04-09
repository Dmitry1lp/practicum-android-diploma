package ru.practicum.android.diploma.feature.filters.ui.region

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import ru.practicum.android.diploma.core.domain.model.GeoArea
import ru.practicum.android.diploma.feature.filters.presentation.region.RegionUiState

//class SelectRegionScreenStateProvider : PreviewParameterProvider<RegionScreenState> {
//
//    private val regions = listOf(
//        "Москва",
//        "Санкт-Петербург",
//        "Московская область",
//        "Ленинградская область",
//        "Республика Татарстан",
//        "Республика Башкортостан",
//        "Свердловская область",
//        "Краснодарский край",
//        "Новосибирская область",
//        "Нижегородская область"
//    )
//
//    private val initScreenState = RegionScreenState()
//
//    override val values: Sequence<RegionScreenState>
//        get() = sequenceOf(
//            initScreenState.copy(uiState = RegionUiState.Loading),
//            initScreenState.copy(uiState = RegionUiState.FetchError),
//            initScreenState.copy(uiState = RegionUiState.NoResult),
//            initScreenState.copy(
//                uiState = RegionUiState.Content(
//                    regions.mapIndexed { index, name ->
//                        GeoArea.Region(
//                            id = index,
//                            name = name,
//                            countryId = index
//                        )
//                    }
//                )
//            )
//        )
//}
