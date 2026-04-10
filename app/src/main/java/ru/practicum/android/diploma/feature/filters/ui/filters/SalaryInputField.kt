package ru.practicum.android.diploma.feature.filters.ui.filters

import android.graphics.Rect
import android.view.ViewTreeObserver
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.app.ui.theme.AppDimensions
import ru.practicum.android.diploma.app.ui.theme.AppTypography
import ru.practicum.android.diploma.app.ui.theme.Black
import ru.practicum.android.diploma.app.ui.theme.Blue
import ru.practicum.android.diploma.app.ui.theme.DiplomaTheme

@Composable
fun SalaryInputField(
    text: String,
    onTextChange: (String) -> Unit
) {
    val focusManager = LocalFocusManager.current
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()
    val isKeyboardVisible by rememberIsKeyboardVisible()

    HandleKeyboardFocus(isKeyboardVisible, isFocused, focusManager)

    Box(
        modifier = Modifier
            .height(AppDimensions.FiltersScreen.heightBoxInputField)
            .padding(horizontal = AppDimensions.paddingMedium),
        contentAlignment = Alignment.Center
    ) {
        SalaryTextField(text, onTextChange, interactionSource, isFocused)
    }
}

@Composable
private fun HandleKeyboardFocus(
    isKeyboardVisible: Boolean,
    isFocused: Boolean,
    focusManager: FocusManager
) {
    LaunchedEffect(isKeyboardVisible) {
        if (!isKeyboardVisible && isFocused) {
            focusManager.clearFocus()
        }
    }
}

@Composable
private fun SalaryTextField(
    text: String,
    onTextChange: (String) -> Unit,
    interactionSource: MutableInteractionSource,
    isFocused: Boolean
) {
    BasicTextField(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                shape = RoundedCornerShape(AppDimensions.FiltersScreen.cornerRadius),
                color = MaterialTheme.colorScheme.surfaceVariant
            ),
        interactionSource = interactionSource,
        value = text,
        onValueChange = { newValue ->
            val filteredValue = newValue.filter { it.isDigit() }
            onTextChange(filteredValue)
        },
        textStyle = AppTypography.bodyLarge.copy(
            color = MaterialTheme.colorScheme.onSecondary,
            textAlign = TextAlign.Start
        ),
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        cursorBrush = SolidColor(Blue),
        decorationBox = { innerTextField ->
            Row(
                modifier = Modifier.padding(AppDimensions.FiltersScreen.rowPaddingTextField),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = stringResource(R.string.label_expected_salary),
                        style = AppTypography.labelMedium,
                        color = calculateColor(text, isFocused)
                    )
                    Box {
                        innerTextField()
                        if (text.isEmpty()) {
                            Text(
                                stringResource(R.string.hint_salary_amount),
                                style = AppTypography.bodyLarge,
                                color = MaterialTheme.colorScheme.outline
                            )
                        }
                    }
                }
                if (text.isNotEmpty() && isFocused) {
                    Icon(
                        modifier = Modifier.clickable(onClick = { onTextChange("") }),
                        painter = painterResource(R.drawable.ic_close_24),
                        contentDescription = null,
                        tint = Black
                    )
                }
            }
        }
    )
}

@Composable
private fun calculateColor(text: String, isFocused: Boolean): Color {
    return when {
        text.isEmpty() && !isFocused -> MaterialTheme.colorScheme.outline
        isFocused -> Blue
        else -> Black
    }
}

@Composable
private fun rememberIsKeyboardVisible(): State<Boolean> {
    val view = LocalView.current
    var keyboardHeight by remember { mutableIntStateOf(0) }

    DisposableEffect(view) {
        val listener = ViewTreeObserver.OnGlobalLayoutListener {
            val rect = Rect()
            view.getWindowVisibleDisplayFrame(rect)
            val screenHeight = view.rootView.height
            keyboardHeight = screenHeight - rect.bottom
        }

        view.viewTreeObserver.addOnGlobalLayoutListener(listener)

        onDispose {
            view.viewTreeObserver.removeOnGlobalLayoutListener(listener)
        }
    }

    return remember {
        derivedStateOf {
            keyboardHeight > view.height * KEYBOARD_THRESHOLD_RATIO
        }
    }
}

private const val KEYBOARD_THRESHOLD_RATIO = 0.15

@Preview
@PreviewLightDark
@Composable
private fun SalaryInputFieldPreviewLightMode() {
    DiplomaTheme {
        SalaryInputField("") { }
    }
}
