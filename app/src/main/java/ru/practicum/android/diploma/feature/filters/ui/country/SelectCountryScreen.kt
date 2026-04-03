package ru.practicum.android.diploma.feature.filters.ui.country

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.app.ui.theme.AppDimensions
import ru.practicum.android.diploma.app.ui.theme.DiplomaTheme
import ru.practicum.android.diploma.core.presentation.components.AppTopBar
import ru.practicum.android.diploma.core.presentation.components.StateInfo
import ru.practicum.android.diploma.feature.filters.ui.FilterItem

@Composable
fun SelectCountryScreen(
    modifier: Modifier = Modifier,
    countries: List<String>? = null,
    onBackClick: () -> Unit = {},
    onCountryClick: () -> Unit = {}
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            AppTopBar(
                title = stringResource(R.string.screen_select_country),
                onNavigationIcon = onBackClick
            )
        }
    ) { innerPaddings ->

        countries?.let {
            LazyColumn(
                modifier = Modifier
                    .padding(innerPaddings)
                    .padding(vertical = AppDimensions.paddingMedium)
            ) {
                items(
                    items = countries,
                    key = { country -> country }
                ) { country ->
                    FilterItem(
                        text = country,
                        onClick = onCountryClick,
                        isActive = true
                    )
                }
            }
        } ?: StateInfo(
            image = R.drawable.img_error_list_fetch,
            text = stringResource(R.string.error_list_fetch)
        )

    }
}

@Preview(showSystemUi = true)
@PreviewLightDark
@Composable
private fun SelectCountryScreenPreview(
    @PreviewParameter(SelectCountryUiStateProvider::class) countries: List<String>?
) {
    DiplomaTheme {
        SelectCountryScreen(
            countries = countries
        )
    }
}
