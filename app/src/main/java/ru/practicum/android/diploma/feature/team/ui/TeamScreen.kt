@file:OptIn(ExperimentalMaterial3Api::class)

package ru.practicum.android.diploma.feature.team.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.app.ui.theme.AppTypography
import ru.practicum.android.diploma.core.presentation.components.AppTopBar
import ru.practicum.android.diploma.core.utils.antiRepetitionClick
import ru.practicum.android.diploma.core.utils.openLink
import ru.practicum.android.diploma.feature.team.domain.Developer

@Composable
fun TeamScreen(
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    //  Данные (позже, если норм, то вынесу в ViewModel)
    val developers = listOf(
        Developer(
            name = "Дмитрий Крылов",
            role = "Team Lead",
            avatarRes = R.drawable.img_empty_favorites,
            github = "https://github.com/dimasla4ee",
            max = null
        ),
        Developer(
            name = "Сергей Аникин",
            role = "Android Developer",
            avatarRes = R.drawable.img_error_list_fetch,
            github = "https://github.com/Nicoanik",
            max = null
        ),
        Developer(
            name = "Дмитрий Перов",
            role = "Android Developer",
            avatarRes = R.drawable.img_fetch_error,
            github = "https://github.com/Dmitry1lp",
            max = null
        ),
        Developer(
            name = "Иван Свиридов",
            role = "Android Developer",
            avatarRes = R.drawable.img_no_internet,
            github = "https://github.com/Sviridov-Ivan",
            max = "https://max.ru/u/f9LHodD0cOJKuUuz0O5gP0tu-H-AizuCP9vRiCDPMRVHFj_dw_xC7Iml3EA"
        ),
    )

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
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            item {
                Text(
                    text = stringResource(R.string.about_team),
                    style = AppTypography.titleLarge,
                    modifier = Modifier.padding(vertical = 16.dp)
                )
            }

            items(developers) { developer ->
                DeveloperItem(
                    developer = developer,
                    onClick = { selectedDeveloper = developer }
                )
            }
        }
    }

    // 🔥 BottomSheet
    if (selectedDeveloper != null) {
        ModalBottomSheet(
            onDismissRequest = { selectedDeveloper = null },
            sheetState = sheetState
        ) {
            DeveloperBottomSheetContent(
                developer = selectedDeveloper!!,
                onGithubClick = { openLink(context, it) },
                onTelegramClick = { openLink(context, it) }
            )
        }
    }
}

@Composable
fun DeveloperItem(
    developer: Developer,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .antiRepetitionClick { onClick() },
        shape = MaterialTheme.shapes.large
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            // 🔹 Аватар
            if (developer.avatarRes != null) {
                Image(
                    painter = painterResource(developer.avatarRes),
                    contentDescription = null,
                    modifier = Modifier
                        .size(56.dp)
                        .clip(CircleShape)
                )
            } else {
                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primary),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = developer.name.first().toString(),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(
                    text = developer.name,
                    style = AppTypography.titleMedium
                )

                Text(
                    text = developer.role,
                    style = AppTypography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
fun DeveloperBottomSheetContent(
    developer: Developer,
    onGithubClick: (String) -> Unit,
    onTelegramClick: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        if (developer.avatarRes != null) {
            Image(
                painter = painterResource(developer.avatarRes),
                contentDescription = null,
                modifier = Modifier
                    .size(96.dp)
                    .clip(CircleShape)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = developer.name,
            style = AppTypography.titleLarge
        )

        Text(
            text = developer.role,
            style = AppTypography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(24.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {

            developer.github?.let {
                AssistChip(
                    onClick = { onGithubClick(it) },
                    label = { Text("GitHub") }
                )
            }

            developer.max?.let {
                AssistChip(
                    onClick = { onTelegramClick(it) },
                    label = { Text("Max") }
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}
