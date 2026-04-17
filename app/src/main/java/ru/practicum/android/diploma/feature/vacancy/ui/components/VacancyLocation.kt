package ru.practicum.android.diploma.feature.vacancy.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.app.ui.theme.AppDimensions
import ru.practicum.android.diploma.app.ui.theme.AppTypography
import ru.practicum.android.diploma.core.domain.model.Address

@Composable
fun VacancyLocation(
    address: Address?,
    regionName: String
) {
    Text(
        modifier = Modifier.padding(top = AppDimensions.paddingMedium),
        text = stringResource(R.string.vacancy_address),
        style = AppTypography.titleMedium
    )

    val addressText = address?.let {
        listOfNotNull(
            address.city,
            address.street,
            address.building
        ).joinToString(", ")
    }.orEmpty()

    val textToShow = addressText.ifBlank {
        regionName
    }

    Text(
        text = textToShow,
        style = AppTypography.bodyLarge
    )
}
