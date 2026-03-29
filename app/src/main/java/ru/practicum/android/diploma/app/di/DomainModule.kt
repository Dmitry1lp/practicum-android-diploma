package ru.practicum.android.diploma.app.di

import org.koin.dsl.module
import ru.practicum.android.diploma.feature.filters.domain.FiltersInteractor
import ru.practicum.android.diploma.feature.filters.domain.FiltersInteractorImpl
import ru.practicum.android.diploma.feature.vacancy.domain.VacancyInteractor

/**
 * Модуль Koin, отвечающий за зависимости Interactor/Usecases
 */
val domainModule = module {

    factory<VacancyInteractor> {
        VacancyInteractor(get())
    }

    factory<FiltersInteractor> {
        FiltersInteractorImpl(get())
    }

}
