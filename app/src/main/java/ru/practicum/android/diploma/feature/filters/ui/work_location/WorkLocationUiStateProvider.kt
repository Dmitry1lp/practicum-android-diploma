package ru.practicum.android.diploma.feature.filters.ui.work_location

import androidx.compose.ui.tooling.preview.PreviewParameterProvider

class WorkLocationUiStateProvider : PreviewParameterProvider<Pair<String?, String?>> {

    override val values: Sequence<Pair<String?, String?>>
        get() = sequenceOf(
            null to null,
            "Россия" to null,
            "Россия" to "Медвежьи-Озёра"
        )

}
