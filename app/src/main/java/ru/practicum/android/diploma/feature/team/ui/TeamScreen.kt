@file:OptIn(ExperimentalMaterial3Api::class)

package ru.practicum.android.diploma.feature.team.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.app.ui.theme.AppDimensions
import ru.practicum.android.diploma.app.ui.theme.AppTypography
import ru.practicum.android.diploma.core.presentation.components.AppTopBar
import ru.practicum.android.diploma.core.utils.openLink
import ru.practicum.android.diploma.feature.team.domain.Developer

@Composable
fun TeamScreen(
    developers: List<Developer>,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var selectedDeveloper by remember { mutableStateOf<Developer?>(null) }
    val sheetState = rememberModalBottomSheetState()

    Scaffold(
        topBar = {
            AppTopBar(title = stringResource(R.string.nav_team))
        }
    ) { paddingValues ->

        LazyColumn(
            modifier = modifier
                .padding(paddingValues)
                .padding(AppDimensions.paddingMedium),
            verticalArrangement = Arrangement.spacedBy(AppDimensions.paddingMedium)
        ) {
            item {
                Text(
                    text = stringResource(R.string.about_team),
                    style = AppTypography.titleLarge,
                    modifier = Modifier.padding(AppDimensions.paddingMedium)
                )
            }

            items(
                items = developers,
                key = { it.name }
            ) { developer ->
                AnimatedVisibility(
                    visible = true,
                    enter = fadeIn() + slideInVertically { it / 2 }
                ) {
                    DeveloperItem(
                        developer = developer,
                        onClick = { selectedDeveloper = developer }
                    )
                }
            }
        }
    }

    // BottomSheet
    if (selectedDeveloper != null) {
        ModalBottomSheet(
            onDismissRequest = { selectedDeveloper = null },
            sheetState = sheetState,
            containerColor = MaterialTheme.colorScheme.surface,
            dragHandle = { BottomSheetDefaults.DragHandle() }
        ) {
            DeveloperBottomSheetContent(
                developer = selectedDeveloper!!,
                onGithubClick = { openLink(context, it) },
                onTelegramClick = { openLink(context, it) }
            )
        }
    }
}
