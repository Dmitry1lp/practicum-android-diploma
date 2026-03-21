@file:OptIn(ExperimentalMaterial3Api::class)

package ru.practicum.android.diploma.app.ui.theme

import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.colorResource
import ru.practicum.android.diploma.R

@Composable
fun appSwitchColors() = SwitchDefaults.colors(
    checkedThumbColor = LocalAppColors.current.switchCheckedThumb,
    checkedTrackColor = LocalAppColors.current.switchCheckedTrack,
    uncheckedThumbColor = LocalAppColors.current.switchUncheckedThumb,
    uncheckedTrackColor = LocalAppColors.current.switchUncheckedTrack
)

@Composable
fun appActionButtonColors() = ButtonDefaults.filledTonalButtonColors().copy(
    containerColor = colorResource(R.color.buttonBackground),
    contentColor = colorResource(R.color.buttonText)
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
