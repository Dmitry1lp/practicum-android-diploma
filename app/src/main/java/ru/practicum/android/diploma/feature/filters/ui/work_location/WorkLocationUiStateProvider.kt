package ru.practicum.android.diploma.feature.filters.ui.work_location

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import ru.practicum.android.diploma.core.domain.model.GeoArea
import ru.practicum.android.diploma.feature.filters.presentation.work_location.WorkLocationUiState

class WorkLocationUiStateProvider : PreviewParameterProvider<Pair<WorkLocationUiState, WorkLocationUiState>> {

    companion object {
        const val REGION_ID = 1620
        const val COUNTRY_ID = 113
    }

    private val region = GeoArea.Region(REGION_ID, "Республика Марий Эл", COUNTRY_ID)
    private val country = GeoArea.Country(COUNTRY_ID, "Россия", listOf(region))

    override val values: Sequence<Pair<WorkLocationUiState, WorkLocationUiState>>
        get() = sequenceOf(
            WorkLocationUiState() to WorkLocationUiState(),
            WorkLocationUiState() to WorkLocationUiState(country),
            WorkLocationUiState() to WorkLocationUiState(country, region),

            WorkLocationUiState(country) to WorkLocationUiState(),
            WorkLocationUiState(country) to WorkLocationUiState(country),
            WorkLocationUiState(country) to WorkLocationUiState(country, region),

            WorkLocationUiState(country, region) to WorkLocationUiState(),
            WorkLocationUiState(country, region) to WorkLocationUiState(country),
            WorkLocationUiState(country, region) to WorkLocationUiState(country, region)
        )

}
