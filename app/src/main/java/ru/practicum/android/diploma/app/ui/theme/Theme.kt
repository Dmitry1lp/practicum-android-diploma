package ru.practicum.android.diploma.app.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

private val LightColorScheme = lightColorScheme(
    primary = Blue, // активный элемент BottomNav, progress bar, курсор
    onPrimary = White,
    secondary = Gray, // неактивный элемент BottomNav, текст (перехода) фильтров
    onSecondary = Black, // текст в поле ввода
    tertiary = Red, // цвет текста в кнопке
    onTertiary = White,
    background = White,
    onBackground = Black, // названия экранов, текст в ошибках поиска, стрелки в фильтрации, текст в фильтрах
    surface = White,
    onSurface = Black,
    surfaceVariant = LightGray, // цвет поля ввода поиска
    onSurfaceVariant = Black,
    inverseSurface = Black,
    inverseOnSurface = White,
    outline = Gray, // hint в строке поиска, текст зп
    outlineVariant = LightGray,
    onError = White,
)

private val DarkColorScheme = darkColorScheme(
    primary = Blue, // активный элемент BottomNav, progress bar, курсор
    onPrimary = White,
    secondary = Gray, // неактивный элемент BottomNav, текст (перехода) фильтров
    onSecondary = Black, // текст в поле ввода
    tertiary = Red, // цвет текста в кнопке
    onTertiary = White,
    background = Black,
    onBackground = BlackNight, // названия экранов, текст в ошибках поиска, стрелки в фильтрации, текст в фильтрах
    onSurface = White,
    surfaceVariant = Gray, // цвет поля ввода поиска
    onSurfaceVariant = Gray,
    inverseSurface = White,
    inverseOnSurface = Black,
    outline = BlackNight, // hint в строке поиска,
    outlineVariant = White,
)

@Composable
fun DiplomaTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    val appColors = if (darkTheme) DarkAppColors else LightAppColors

    CompositionLocalProvider(
        LocalAppColors provides appColors
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = AppTypography,
            content = content
        )
    }
}
