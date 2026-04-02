@file:OptIn(ExperimentalMaterial3Api::class)

package ru.practicum.android.diploma.core.presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.app.ui.theme.AppDimensions
import ru.practicum.android.diploma.app.ui.theme.AppTypography
import ru.practicum.android.diploma.app.ui.theme.appTopBarColors

@Composable
fun AppTopBar(
    modifier: Modifier = Modifier,
    title: String,
    onNavigationIcon: (() -> Unit)? = null,
    action1: TopBarIcon? = null,
    action2: TopBarIcon? = null
) {
    TopAppBar(
        colors = appTopBarColors(),
        modifier = modifier.padding(end = AppDimensions.endPaddingTopBar),
        title = {
            Text(
                text = title,
                style = AppTypography.titleMedium
            )
        },
        navigationIcon = {
            if (onNavigationIcon != null) {
                IconButton(onClick = onNavigationIcon) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_arrow_back_24),
                        contentDescription = "Back"
                    )
                }
            }
        },
        actions = {
            if (action1 != null) {
                IconButton(onClick = action1.onClick) {
                    Icon(
                        painter = painterResource(id = action1.iconResId),
                        contentDescription = action1.contentDescription,
                        tint = when {
                            action1.iconResId == R.drawable.ic_favorites_on_24 -> Color.Unspecified
                            else -> MaterialTheme.colorScheme.onSurface
                        }
                    )
                }
            }
            if (action2 != null) {
                IconButton(onClick = action2.onClick) {
                    Icon(
                        painter = painterResource(id = action2.iconResId),
                        contentDescription = action2.contentDescription,
                        tint = when {
                            action2.iconResId == R.drawable.ic_favorites_on_24 -> Color.Unspecified
                            else -> MaterialTheme.colorScheme.onSurface
                        }
                    )
                }
            }
        }
    )
}

data class TopBarIcon(
    val iconResId: Int,
    val contentDescription: String? = null,
    val onClick: () -> Unit
)
