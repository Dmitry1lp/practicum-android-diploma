package ru.practicum.android.diploma.core.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.app.ui.theme.AppDimensions
import ru.practicum.android.diploma.app.ui.theme.AppTypography
import ru.practicum.android.diploma.app.ui.theme.DiplomaTheme
import ru.practicum.android.diploma.core.utils.applyIf

@Composable
fun ImageListItem(
    modifier: Modifier = Modifier,
    model: Any? = null,
    imageBorder: BorderStroke? = null,
    imageContentDescription: String? = null,
    content: @Composable () -> Unit
) {
    val shape = remember { RoundedCornerShape(AppDimensions.ImageRowListItem.imageCornerRadius) }
    val borderIsApplied = remember { imageBorder != null }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalAlignment = Alignment.Top
    ) {
        AsyncImage(
            modifier = Modifier
                .size(AppDimensions.ImageRowListItem.imageSize)
                .applyIf(borderIsApplied) {
                    border(
                        border = imageBorder!!,
                        shape = shape
                    )
                }
                .clip(shape),
            model = model,
            contentDescription = imageContentDescription,
            placeholder = painterResource(R.drawable.placeholder_32),
            error = painterResource(R.drawable.placeholder_32),
            contentScale = ContentScale.Crop
        )

        content()
    }
}

@Preview
@Composable
private fun ImageListItemPreview() {
    DiplomaTheme {
        ImageListItem(
            model = null,
            modifier = Modifier
                .height(82.dp)
                .width(360.dp)
        ) {
            Column {
                Text(
                    text = "Text",
                    style = AppTypography.titleMedium
                )
                Text(
                    text = "Text",
                    style = AppTypography.bodyLarge
                )
                Text(
                    text = "Text",
                    style = AppTypography.bodyLarge
                )
            }
        }
    }
}
