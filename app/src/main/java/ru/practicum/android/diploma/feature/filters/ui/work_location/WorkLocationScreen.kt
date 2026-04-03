package ru.practicum.android.diploma.feature.filters.ui.work_location

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.app.ui.theme.AppDimensions
import ru.practicum.android.diploma.app.ui.theme.AppTypography
import ru.practicum.android.diploma.app.ui.theme.DiplomaTheme
import ru.practicum.android.diploma.app.ui.theme.primaryButtonColors
import ru.practicum.android.diploma.core.presentation.components.AppTopBar
import ru.practicum.android.diploma.feature.filters.ui.HintedFilterItem
import ru.practicum.android.diploma.feature.filters.ui.InactiveFilterItem

@Composable
fun WorkLocationScreen(
    modifier: Modifier = Modifier,
    country: String? = null,
    region: String? = null,
    onBackClick: () -> Unit = {},
    onCountryClick: () -> Unit = {},
    onRegionClick: () -> Unit = {},
    onSelectClick: () -> Unit = {}
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            AppTopBar(
                title = stringResource(R.string.screen_work_location),
                onNavigationIcon = onBackClick
            )
        }
    ) { innerPaddings ->
        val actions = listOf(
            Triple(country, R.string.filter_country, onCountryClick),
            Triple(region, R.string.filter_region, onRegionClick)
        )

        Column(
            modifier = Modifier.padding(innerPaddings)
        ) {
            actions.forEach { (text, id, action) ->
                text?.let {
                    HintedFilterItem(
                        text = text,
                        hint = stringResource(id),
                        onClick = action
                    )
                } ?: InactiveFilterItem(
                    text = stringResource(id),
                    onClick = action
                )
            }

            Spacer(Modifier.weight(1f))

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(AppDimensions.FiltersScreen.heightButton)
                    .padding(horizontal = AppDimensions.paddingMedium),
                shape = RoundedCornerShape(AppDimensions.FiltersScreen.cornerRadius),
                colors = primaryButtonColors(),
                onClick = onSelectClick
            ) {
                Text(
                    text = stringResource(R.string.button_apply),
                    style = AppTypography.titleSmall
                )
            }
        }

    }
}

@Preview(showSystemUi = true)
@PreviewLightDark
@Composable
private fun WorkLocationScreenPreview(
    @PreviewParameter(WorkLocationUiStateProvider::class) area: Pair<String?, String?>
) {
    DiplomaTheme {
        WorkLocationScreen(
            country = area.first,
            region = area.second
        )
    }
}
