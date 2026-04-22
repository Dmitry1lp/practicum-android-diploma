package ru.practicum.android.diploma.app.di

import android.content.Context
import androidx.room.Room
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.practicum.android.diploma.BuildConfig
import ru.practicum.android.diploma.BuildConfig.DIPLOMA_PREFERENCES
import ru.practicum.android.diploma.core.config.ApiConfig
import ru.practicum.android.diploma.core.config.DatabaseConfig
import ru.practicum.android.diploma.core.data.database.dao.FavoritesDao
import ru.practicum.android.diploma.core.data.database.db.AppDatabase
import ru.practicum.android.diploma.core.data.network.api.VacancyApi
import ru.practicum.android.diploma.core.data.network.client.NetworkClient
import ru.practicum.android.diploma.core.data.network.client.RetrofitNetworkClient
import ru.practicum.android.diploma.core.domain.repository.FavoritesRepository
import ru.practicum.android.diploma.feature.favorite.data.FavoritesRepositoryImpl
import ru.practicum.android.diploma.feature.filters.data.repository.FiltersRepositoryImpl
import ru.practicum.android.diploma.feature.filters.domain.repository.FiltersRepository
import ru.practicum.android.diploma.feature.search.data.repository.SearchRepositoryImpl
import ru.practicum.android.diploma.feature.search.domain.repository.SearchRepository
import ru.practicum.android.diploma.feature.vacancy.data.VacancyDetailsRepositoryImpl
import ru.practicum.android.diploma.feature.vacancy.domain.VacancyDetailsRepository
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.util.DebugLogger

/**
 * Модуль Koin, отвечающий за зависимости Repository и Data sources
 */
val dataModule = module {

    factory<NetworkClient> {
        RetrofitNetworkClient(get())
    }

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
            favoritesRepository = get()
        )
    }
    single<AppDatabase> {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            DatabaseConfig.DATABASE_NAME
        )
            .fallbackToDestructiveMigration(true)
            .build()
    }

    single<FavoritesDao> {
        get<AppDatabase>().favoritesDao()
    }

    single<FavoritesRepository> {
        FavoritesRepositoryImpl(get<FavoritesDao>())
    }

    single<SearchRepository> {
        SearchRepositoryImpl(
            networkClient = get(),
        )
    }
    single<FiltersRepository> {
        FiltersRepositoryImpl(get(), get())
    }

    single {
        androidContext().getSharedPreferences(DIPLOMA_PREFERENCES, Context.MODE_PRIVATE)
    }

    single<ImageLoader> {
        ImageLoader.Builder(androidContext())
            .components {
                add(SvgDecoder.Factory())
            }
            .crossfade(true)
            .build()
    }

}
