package ru.practicum.android.diploma.app.di

import org.koin.dsl.module
import ru.practicum.android.diploma.feature.favorite.domain.FavoritesInteractor
import ru.practicum.android.diploma.feature.favorite.domain.FavoritesInteractorImpl
import ru.practicum.android.diploma.feature.filters.domain.FiltersInteractor
import ru.practicum.android.diploma.feature.filters.domain.FiltersInteractorImpl
import ru.practicum.android.diploma.feature.search.domain.interactor.SearchInteractor
import ru.practicum.android.diploma.feature.search.domain.interactor.SearchInteractorImpl
import ru.practicum.android.diploma.feature.vacancy.domain.VacancyDetailsInteractor

/**
 * Модуль Koin, отвечающий за зависимости Interactor/Usecases
 */
val domainModule = module {

    factory<VacancyDetailsInteractor> {
        VacancyDetailsInteractor(get())
    }

    single<SearchInteractor> {
        SearchInteractorImpl(get())
    }

    factory<FiltersInteractor> {
        FiltersInteractorImpl(get())
    }

    factory<FavoritesInteractor> {
        FavoritesInteractorImpl(get())
    }

}
