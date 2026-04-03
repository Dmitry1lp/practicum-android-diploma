package ru.practicum.android.diploma.feature.filters.ui

import androidx.compose.ui.tooling.preview.PreviewParameterProvider

class WorkLocationUiStateProvider : PreviewParameterProvider<Pair<String?, String?>> {

    override val values: Sequence<Pair<String?, String?>>
        get() = sequenceOf(
            null to null,
            "Россия" to null,
            null to "Медвежьи-Озёра",
            "Россия" to "Медвежьи-Озёра"
        )

}
