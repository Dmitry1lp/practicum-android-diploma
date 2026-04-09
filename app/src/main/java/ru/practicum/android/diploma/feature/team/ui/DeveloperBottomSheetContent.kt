package ru.practicum.android.diploma.feature.team.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.AssistChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import ru.practicum.android.diploma.app.ui.theme.AppDimensions
import ru.practicum.android.diploma.app.ui.theme.AppTypography
import ru.practicum.android.diploma.feature.team.domain.Developer

@Composable
fun DeveloperBottomSheetContent(
    developer: Developer,
    onGithubClick: (String) -> Unit,
    onTelegramClick: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(AppDimensions.paddingBig),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (developer.avatarRes != null) {
            Image(
                painter = painterResource(developer.avatarRes),
                contentDescription = null,
                modifier = Modifier
                    .size(AppDimensions.teamIconSizeBs)
                    .clip(CircleShape)
            )
        }

        Spacer(modifier = Modifier.height(AppDimensions.paddingMedium))

        Text(
            text = developer.name,
            style = AppTypography.titleLarge
        )

        Text(
            text = developer.role,
            style = AppTypography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(AppDimensions.paddingBig))

        Row(horizontalArrangement = Arrangement.spacedBy(AppDimensions.paddingMedium)) {
            developer.github?.let {
                AssistChip(
                    onClick = { onGithubClick(it) },
                    label = { Text("GitHub") }
                )
            }

            developer.telegram?.let {
                AssistChip(
                    onClick = { onTelegramClick(it) },
                    label = { Text("Telegram") }
                )
            }
        }

        Spacer(modifier = Modifier.height(AppDimensions.paddingBig))
    }
}
