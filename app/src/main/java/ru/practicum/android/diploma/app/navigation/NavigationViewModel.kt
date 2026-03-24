package ru.practicum.android.diploma.app.navigation

import androidx.lifecycle.ViewModel
import androidx.navigation3.runtime.NavKey

class NavigationViewModel : ViewModel() {
    val backStack = TopLevelBackStack<NavKey>(startKey = Route.Search)
}
