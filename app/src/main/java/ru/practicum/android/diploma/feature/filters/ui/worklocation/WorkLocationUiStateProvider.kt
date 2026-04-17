package ru.practicum.android.diploma.feature.filters.ui.worklocation

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import ru.practicum.android.diploma.core.domain.model.GeoArea
import ru.practicum.android.diploma.feature.filters.presentation.worklocation.WorkLocation

class WorkLocationUiStateProvider : PreviewParameterProvider<Pair<WorkLocation, WorkLocation>> {

    companion object {
        const val REGION_ID = 1620
        const val COUNTRY_ID = 113
    }

    private val region = GeoArea.Region(REGION_ID, "Республика Марий Эл", COUNTRY_ID)
    private val country = GeoArea.Country(COUNTRY_ID, "Россия", listOf(region))

    override val values: Sequence<Pair<WorkLocation, WorkLocation>>
        get() = sequenceOf(
            WorkLocation() to WorkLocation(),
            WorkLocation() to WorkLocation(country),
            WorkLocation() to WorkLocation(country, region),

            WorkLocation(country) to WorkLocation(),
            WorkLocation(country) to WorkLocation(country),
            WorkLocation(country) to WorkLocation(country, region),

            WorkLocation(country, region) to WorkLocation(),
            WorkLocation(country, region) to WorkLocation(country),
            WorkLocation(country, region) to WorkLocation(country, region)
        )

}
