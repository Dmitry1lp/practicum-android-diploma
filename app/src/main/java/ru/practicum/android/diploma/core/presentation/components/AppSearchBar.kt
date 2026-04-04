package ru.practicum.android.diploma.core.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.app.ui.theme.AppDimensions
import ru.practicum.android.diploma.app.ui.theme.appSearchBarColors

@Composable
fun AppSearchBar(
    modifier: Modifier = Modifier,
    text: String,
    hint: String,
    onTextChange: (String) -> Unit,
    onAction: () -> Unit = {}
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    // сам поиск
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(AppDimensions.AppSearchBar.paddings),
        contentAlignment = Alignment.Center
    ) {
        OutlinedTextField(
            value = text,
            onValueChange = onTextChange,
            placeholder = {
                Text(
                    text = hint,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.outline
                )
            },
            singleLine = true,
            // иконка лупа\крестик
            trailingIcon = {
                if (text.isNotEmpty()) {
                    Icon(
                        painterResource(R.drawable.ic_close_24),
                        tint = MaterialTheme.colorScheme.onSecondary,
                        contentDescription = null,
                        modifier = modifier.clickable {
                            onTextChange("")
                            keyboardController?.hide()
                        }
                    )
                } else {
                    Icon(
                        painterResource(R.drawable.ic_search_24),
                        tint = MaterialTheme.colorScheme.onSecondary,
                        contentDescription = null
                    )
                }
            },
            shape = RoundedCornerShape(AppDimensions.AppSearchBar.roundedCornerShape),
            colors = appSearchBarColors(),
            modifier = modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search  // или ImeAction.Done, ImeAction.Go
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    onAction()
                }
            )
        )
    }
}
