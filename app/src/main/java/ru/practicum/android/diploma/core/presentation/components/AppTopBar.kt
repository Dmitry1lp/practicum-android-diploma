@file:OptIn(ExperimentalMaterial3Api::class)

package ru.practicum.android.diploma.core.presentation.components

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
import ru.practicum.android.diploma.app.ui.theme.AppTypography


data class TopBarIcon(
    val iconResId: Int,
    val onClick: () -> Unit
)

@Composable
fun AppTopBar(
    modifier: Modifier = Modifier,
    title: String,
    onNavigationIcon: (() -> Unit)? = null,
    action1: TopBarIcon? = null,
    action2: TopBarIcon? = null
) {
    val tint = if (action2?.iconResId == R.drawable.ic_favorites_on_24) {
        Color.Unspecified
    } else {
        MaterialTheme.colorScheme.onSurface
    }

    TopAppBar(
        modifier = modifier,
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
                        contentDescription = "Action 1",
                        tint = tint
                    )
                }
            }
            if (action2 != null) {
                IconButton(onClick = action2.onClick) {
                    Icon(
                        painter = painterResource(id = action2.iconResId),
                        contentDescription = "Action 2",
                        tint = tint
                    )
                }
            }
        }
    )
}
