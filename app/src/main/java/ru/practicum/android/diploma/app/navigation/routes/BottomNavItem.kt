package ru.practicum.android.diploma.app.navigation.routes

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.navigation3.runtime.NavKey

/**
 * Интерфейс для пунктов нижнего меню навигации.
 *
 * @property icon [DrawableRes] иконка пункта меню
 * @property label [StringRes] текст подписи пункта меню
 */
interface BottomNavItem : NavKey {
    @get:DrawableRes
    val icon: Int

    @get:StringRes
    val label: Int
}
