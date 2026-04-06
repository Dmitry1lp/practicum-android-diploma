package ru.practicum.android.diploma.feature.team.ui

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import ru.practicum.android.diploma.app.ui.theme.AppDimensions
import ru.practicum.android.diploma.app.ui.theme.AppTypography
import ru.practicum.android.diploma.core.utils.antiRepetitionClick
import ru.practicum.android.diploma.feature.team.domain.Developer

@Composable
fun DeveloperItem(
    developer: Developer,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val scale by animateFloatAsState(targetValue = if (isPressed) 0.97f else 1f, label = "")

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
            }
            .antiRepetitionClick { onClick() },
        shape = MaterialTheme.shapes.large
    ) {
        Row(
            modifier = Modifier.padding(AppDimensions.paddingMedium),
            verticalAlignment = Alignment.CenterVertically
        ) {

            // Аватар
            if (developer.avatarRes != null) {
                Image(
                    painter = painterResource(developer.avatarRes),
                    contentDescription = null,
                    modifier = Modifier
                        .size(AppDimensions.teamIconSizeItem)
                        .clip(CircleShape)
                )
            } else {
                Box(
                    modifier = Modifier
                        .size(AppDimensions.teamIconSizeItem)
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

            Spacer(modifier = Modifier.width(AppDimensions.paddingMedium))

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
