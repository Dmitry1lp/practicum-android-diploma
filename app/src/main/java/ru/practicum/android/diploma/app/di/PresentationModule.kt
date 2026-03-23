package ru.practicum.android.diploma.app.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.app.navigation.NavigationViewModel

/**
 * Модуль Koin, отвечающий за зависимости UI и ViewModel
 */
val presentationModule = module {

    viewModel<NavigationViewModel> {
        NavigationViewModel()
    }

}
