package ru.practicum.android.diploma.app.navigation

import androidx.lifecycle.ViewModel
import androidx.navigation3.runtime.NavKey
import ru.practicum.android.diploma.app.navigation.routes.Route

class NavigationViewModel : ViewModel() {
    val backStack = TopLevelBackStack<NavKey>(startKey = Route.Search)
}
