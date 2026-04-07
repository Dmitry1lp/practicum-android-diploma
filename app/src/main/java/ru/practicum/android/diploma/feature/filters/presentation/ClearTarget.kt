package ru.practicum.android.diploma.feature.filters.presentation

/**
 * Определяет цель очистки данных.
 */
sealed interface ClearTarget {
    data object AppPreferences : ClearTarget
    data object All : ClearTarget
    data object Industry : ClearTarget
    data object WorkLocation : ClearTarget
    data object Country : ClearTarget
    data object Region : ClearTarget
}
