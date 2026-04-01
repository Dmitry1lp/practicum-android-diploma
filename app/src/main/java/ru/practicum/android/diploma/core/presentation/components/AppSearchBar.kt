package ru.practicum.android.diploma.core.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.app.ui.theme.AppDimensions

@Composable
fun AppSearchBar(
    text: String,
    hint: String,
    isClearVisible: Boolean,
    onTextChange: (String) -> Unit,
    onClearClick: () -> Unit,
    modifier: Modifier = Modifier
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
                if (isClearVisible) {
                    IconButton(onClick = {
                        onClearClick()
                        keyboardController?.hide()
                    }) {
                        Icon(
                            painterResource(R.drawable.ic_close_24),
                            tint = MaterialTheme.colorScheme.onSecondary,
                            contentDescription = null
                        )
                    }
                } else {
                    Icon(
                        painterResource(R.drawable.ic_search_24),
                        tint = MaterialTheme.colorScheme.onSecondary,
                        contentDescription = null
                    )
                }
            },
            shape = RoundedCornerShape(AppDimensions.AppSearchBar.roundedCornerShape),
            colors = TextFieldDefaults.colors(

                // фон
                focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,

                // курсор
                cursorColor = MaterialTheme.colorScheme.primary,

                // бордер
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedTextColor = MaterialTheme.colorScheme.onSecondary
            ),
            modifier = modifier.fillMaxWidth()
        )
    }
}
