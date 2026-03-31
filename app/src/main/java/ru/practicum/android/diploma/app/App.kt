package ru.practicum.android.diploma.app

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import ru.practicum.android.diploma.app.di.dataModule
import ru.practicum.android.diploma.app.di.domainModule
import ru.practicum.android.diploma.app.di.presentationModule

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        // Запуск Koin
        startKoin {
            androidContext(this@App)
            modules(
                presentationModule,
                domainModule,
                dataModule
            )
        }
    }
}
