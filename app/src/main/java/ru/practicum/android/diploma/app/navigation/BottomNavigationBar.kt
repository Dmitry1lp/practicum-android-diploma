package ru.practicum.android.diploma.app.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.NavKey
import ru.practicum.android.diploma.app.navigation.routes.BottomNavItem
import ru.practicum.android.diploma.app.ui.theme.LocalAppColors
import ru.practicum.android.diploma.app.ui.theme.appNavBarItemColors

@Composable
fun BottomNavigationBar(
    bottomNavItems: List<BottomNavItem>,
    topLevelBackStack: TopLevelBackStack<NavKey>
) {
    Column {
        HorizontalDivider(color = LocalAppColors.current.bottomNavBarDivider)

        NavigationBar(
            containerColor = MaterialTheme.colorScheme.background,
            tonalElevation = 0.dp
        ) {
            bottomNavItems.forEach { item ->
                AppNavBarItem(
                    item = item,
                    topLevelBackStack = topLevelBackStack
                )
            }
        }
    }

}

@Composable
fun RowScope.AppNavBarItem(
    item: BottomNavItem,
    topLevelBackStack: TopLevelBackStack<NavKey>
) {
    val selected = topLevelBackStack.topLevelKey == item

    NavigationBarItem(
        colors = appNavBarItemColors(),
        selected = selected,
        onClick = { topLevelBackStack.switchTopLevel(item) },
        icon = {
            Icon(
                painter = painterResource(item.icon),
                contentDescription = null
            )
        },
        label = {
            Text(
                text = stringResource(item.label),
                style = MaterialTheme.typography.labelMedium,
            )
        }
    )
}
