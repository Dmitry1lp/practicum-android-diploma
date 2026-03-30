package ru.practicum.android.diploma.app.di

import org.koin.dsl.module
import ru.practicum.android.diploma.feature.search.domain.interactor.SearchInteractor
import ru.practicum.android.diploma.feature.search.domain.interactor.SearchInteractorImpl
import ru.practicum.android.diploma.feature.vacancy.domain.VacancyInteractor

/**
 * Модуль Koin, отвечающий за зависимости Interactor/Usecases
 */
val domainModule = module {

    factory<VacancyInteractor> {
        VacancyInteractor(get())
    }

    single<SearchInteractor> {
        SearchInteractorImpl(get())
    }
}
