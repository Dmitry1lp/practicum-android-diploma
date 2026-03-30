package ru.practicum.android.diploma.app.di

import androidx.room.Room
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.practicum.android.diploma.BuildConfig
import ru.practicum.android.diploma.core.config.ApiConfig
import ru.practicum.android.diploma.core.config.DatabaseConfig
import ru.practicum.android.diploma.core.data.database.dao.FavoritesDao
import ru.practicum.android.diploma.core.data.database.db.AppDatabase
import ru.practicum.android.diploma.core.data.network.api.VacancyApi
import ru.practicum.android.diploma.core.data.network.client.NetworkClient
import ru.practicum.android.diploma.core.data.network.client.RetrofitNetworkClient
import ru.practicum.android.diploma.feature.search.data.repository.VacancyRepositoryImpl
import ru.practicum.android.diploma.feature.search.domain.repository.VacancyRepository
import ru.practicum.android.diploma.feature.search.data.repository.VacancyRepositoryImpl
import ru.practicum.android.diploma.feature.search.domain.repository.VacancyRepository
import ru.practicum.android.diploma.core.domain.repository.FavoritesRepository
import ru.practicum.android.diploma.feature.favorite.data.FavoritesRepositoryImpl
import ru.practicum.android.diploma.feature.vacancy.data.VacancyDetailsRepositoryImpl
import ru.practicum.android.diploma.feature.vacancy.domain.VacancyDetailsRepository

/**
 * Модуль Koin, отвечающий за зависимости Repository и Data sources
 */
val dataModule = module {

    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl(ApiConfig.BASE_URL)
            .client(get<OkHttpClient>())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single<OkHttpClient> {
        OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request()
                    .newBuilder()
                    .addHeader(
                        "Authorization",
                        "Bearer ${BuildConfig.API_ACCESS_TOKEN}"
                    )
                    .build()

                chain.proceed(request)
            }
            .build()
    }

    single<VacancyApi> {
        get<Retrofit>().create(VacancyApi::class.java)
    }

    single<NetworkClient> {
        RetrofitNetworkClient(
            get<VacancyApi>()
        )
    }

    single<VacancyDetailsRepository> {
        VacancyDetailsRepositoryImpl(
            networkClient = get(),
        )
    }
    single<AppDatabase> {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            DatabaseConfig.DATABASE_NAME
        )
            .fallbackToDestructiveMigration(false)
            .build()
    }

    single<FavoritesDao> {
        get<AppDatabase>().favoritesDao()
    }

    single<FavoritesRepository> {
        FavoritesRepositoryImpl(get<FavoritesDao>())
    }

    single<VacancyRepository> {
        VacancyRepositoryImpl(
            networkClient = get(),
        )
    }

}
