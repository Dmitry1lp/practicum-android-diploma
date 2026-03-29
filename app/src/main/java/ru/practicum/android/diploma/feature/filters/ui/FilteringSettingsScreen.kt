package ru.practicum.android.diploma.feature.filters.ui

import android.graphics.Rect
import android.view.ViewTreeObserver
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.app.ui.theme.AppTypography
import ru.practicum.android.diploma.app.ui.theme.Black
import ru.practicum.android.diploma.app.ui.theme.Blue
import ru.practicum.android.diploma.app.ui.theme.DiplomaTheme
import ru.practicum.android.diploma.core.presentation.components.AppTopBar
import androidx.compose.runtime.State
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.MutableStateFlow
import ru.practicum.android.diploma.app.ui.theme.AppDimensions
import ru.practicum.android.diploma.app.ui.theme.Red
import ru.practicum.android.diploma.feature.filters.presentation.FiltersState

@Composable
fun FilteringSettingsScreen(
    stateViewModel: MutableStateFlow<FiltersState>,
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    onIndustriesScreenNavigate: () -> Unit,
    onSwitchClick: () -> Unit
) {
    val state by stateViewModel.collectAsStateWithLifecycle()

//    var checkbox by remember { mutableStateOf(false) }
    var text by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            AppTopBar(
                title = stringResource(R.string.screen_filter_settings),
                onNavigationIcon = onBackClick
            )
        }
    ) { paddingValues ->
        Column(
            modifier = modifier.padding(paddingValues)
        ) {
            InactiveFilterItem(
                text = stringResource(R.string.filter_work_location),
                onClick = {}
            )
            InactiveFilterItem(
                text = stringResource(R.string.filter_industry),
                onClick = onIndustriesScreenNavigate
            )
            SalaryInputField(
                text = text,
                onTextChange = { text = it }
            )
            SwitchFilterItem(
                text = stringResource(R.string.checkbox_hide_without_salary),
                checked = state.isCheckedSwitch,
                onCheckedChange = onSwitchClick
            )
            Spacer(modifier = Modifier.weight(1f))
            if (text.isNotEmpty()) {
                Column(
                    modifier = Modifier.padding(horizontal = 17.dp)
                ) {
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(AppDimensions.FiltersScreen.heightButton),
                        shape = RoundedCornerShape(AppDimensions.FiltersScreen.cornerRadius),
                        onClick = {}
                    ) {
                        Text(
                            text = stringResource(R.string.button_apply),
                            style = AppTypography.titleSmall
                        )
                    }
                    Text(
                        modifier = Modifier
                            .padding(AppDimensions.FiltersScreen.resetButtonPadding)
                            .align(Alignment.CenterHorizontally)
                            .clickable(onClick = { text = "" }),
                        text = stringResource(R.string.button_reset),
                        style = AppTypography.titleSmall,
                        color = Red
                    )
                }
            }
        }
    }
}

@Composable
private fun SalaryInputField(
    text: String,
    onTextChange: (String) -> Unit
) {
    val focusManager = LocalFocusManager.current
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()
    val isKeyboardVisible by rememberIsKeyboardVisible()

    LaunchedEffect(isKeyboardVisible) {
        if (!isKeyboardVisible && isFocused) {
            focusManager.clearFocus()
        }
    }

    Box(
        modifier = Modifier
            .height(AppDimensions.FiltersScreen.heightBoxInputField)
            .padding(horizontal = AppDimensions.paddingMedium),
        contentAlignment = Alignment.Center
    ) {
        BasicTextField(
            modifier = Modifier
                .fillMaxWidth()
                .height(AppDimensions.FiltersScreen.heightTextField)
                .background(
                    shape = RoundedCornerShape(AppDimensions.FiltersScreen.cornerRadius),
                    color = MaterialTheme.colorScheme.surfaceVariant
                ),
            interactionSource = interactionSource,
            value = text,
            onValueChange = onTextChange,
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
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = stringResource(R.string.label_expected_salary),
                            style = AppTypography.labelMedium,
                            color = when {
                                text.isEmpty() && !isFocused -> MaterialTheme.colorScheme.outline
                                isFocused -> Blue
                                else -> Black
                            }
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
}


@Composable
fun rememberIsKeyboardVisible(): State<Boolean> {
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
            keyboardHeight > view.height * 0.15
        }
    }
}

@Preview
@Composable
private fun FilteringSettingsScreenPreviewLightMode() {
    DiplomaTheme(false) {
        FilteringSettingsScreen(
            stateViewModel = MutableStateFlow(FiltersState()),
            onBackClick = {},
            onIndustriesScreenNavigate = {},
            onSwitchClick = {}
        )
    }
}

@Preview
@Composable
private fun FilteringSettingsScreenPreviewDarkMode() {
    DiplomaTheme(true) {
        FilteringSettingsScreen(
            stateViewModel = MutableStateFlow(FiltersState()),
            onBackClick = {},
            onIndustriesScreenNavigate = {},
            onSwitchClick = {}
        )
    }
}
