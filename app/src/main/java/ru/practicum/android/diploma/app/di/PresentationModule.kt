package ru.practicum.android.diploma.app.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.app.navigation.NavigationViewModel
import ru.practicum.android.diploma.feature.filters.presentation.FiltersViewModel
import ru.practicum.android.diploma.core.domain.repository.FavoritesRepository
import ru.practicum.android.diploma.feature.favorite.presentation.FavoritesViewModel
import ru.practicum.android.diploma.feature.search.domain.repository.VacancyRepository
import ru.practicum.android.diploma.feature.search.ui.SearchViewModel
import ru.practicum.android.diploma.feature.vacancy.presentation.VacancyDetailsViewModel

/**
 * Модуль Koin, отвечающий за зависимости UI и ViewModel
 */
val presentationModule = module {

    viewModel<NavigationViewModel> {
        NavigationViewModel()
    }

    viewModel<VacancyDetailsViewModel> { (vacancyId: String) ->
        VacancyDetailsViewModel(
            interactor = get(),
            vacancyId = vacancyId
        )
    }

    viewModel<SearchViewModel> {
        SearchViewModel(
            get<VacancyRepository>()
        )
    }

    viewModel<FavoritesViewModel> {
        FavoritesViewModel(
            get<FavoritesRepository>()
        )
    }

    viewModel<FiltersViewModel> {
        FiltersViewModel(get())
    }

}
