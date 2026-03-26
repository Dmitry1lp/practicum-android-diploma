@file:OptIn(ExperimentalMaterial3Api::class)

package ru.practicum.android.diploma.app.ui.theme

import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun primaryButtonColors() = ButtonDefaults.buttonColors(
    containerColor = LocalAppColors.current.primaryButtonContainer,
    contentColor = LocalAppColors.current.primaryButtonContent
)

@Composable
fun tertiaryButtonColors() = ButtonDefaults.buttonColors(
    containerColor = LocalAppColors.current.tertiaryButtonContainer,
    contentColor = LocalAppColors.current.tertiaryButtonContent
)

@Composable
fun appSearchBarColors() = SearchBarDefaults.colors(
    containerColor = LocalAppColors.current.searchBarBackground,
    inputFieldColors = SearchBarDefaults.inputFieldColors(
        focusedTextColor = LocalAppColors.current.searchBarText,
        unfocusedTextColor = LocalAppColors.current.searchBarText,
        focusedPlaceholderColor = LocalAppColors.current.searchBarHint,
        unfocusedPlaceholderColor = LocalAppColors.current.searchBarHint,
        focusedLeadingIconColor = LocalAppColors.current.searchBarIcon,
        unfocusedLeadingIconColor = LocalAppColors.current.searchBarIcon,
        focusedTrailingIconColor = LocalAppColors.current.searchBarIcon,
        unfocusedTrailingIconColor = LocalAppColors.current.searchBarIcon
    )
)

@Composable
fun appNavBarItemColors() = NavigationBarItemDefaults.colors(
    selectedIconColor = MaterialTheme.colorScheme.primary,
    selectedTextColor = MaterialTheme.colorScheme.primary,
    unselectedIconColor = MaterialTheme.colorScheme.secondary,
    unselectedTextColor = MaterialTheme.colorScheme.secondary,
    indicatorColor = Color.Transparent
)

@Composable
fun appCheckboxColors() = CheckboxDefaults.colors(
    checkedColor = MaterialTheme.colorScheme.primary,
    uncheckedColor = MaterialTheme.colorScheme.primary
)

@Composable
fun appCardColors() = CardDefaults.cardColors(
    containerColor = LocalAppColors.current.cardBackground,
    contentColor = LocalAppColors.current.cardText
)
