package ru.practicum.android.diploma.app.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.app.navigation.NavigationViewModel
import ru.practicum.android.diploma.feature.search.domain.repository.VacancyRepository
import ru.practicum.android.diploma.feature.search.ui.SearchViewModel
import ru.practicum.android.diploma.feature.vacancy.presentation.VacancyViewModel

/**
 * Модуль Koin, отвечающий за зависимости UI и ViewModel
 */
val presentationModule = module {

    viewModel<NavigationViewModel> {
        NavigationViewModel()
    }

    viewModel<VacancyViewModel> { (vacancyId: String) ->
        VacancyViewModel(
            interactor = get(),
            vacancyId = vacancyId
        )
    }

    viewModel<SearchViewModel> {
        SearchViewModel(
            get<VacancyRepository>()
        )
    }
}
