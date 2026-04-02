package ru.practicum.android.diploma.feature.search.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.app.ui.theme.DiplomaTheme
import ru.practicum.android.diploma.core.presentation.components.AppSearchBar

@Composable
fun SearchBar(
    text: String,
    onTextChange: (String) -> Unit,
) {
    AppSearchBar(
        text = text,
        hint = stringResource(R.string.hint_search_vacancy),
        onTextChange = onTextChange,
    )
}

@Preview(showBackground = true)
@Composable
private fun SearchBarPreview() {
    val textState = remember { mutableStateOf("Введите запрос") }

    DiplomaTheme {
        SearchBar(
            text = textState.value,
            onTextChange = { textState.value = it },
        )
    }
}
