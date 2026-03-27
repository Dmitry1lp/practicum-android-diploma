package ru.practicum.android.diploma.app.di

import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.practicum.android.diploma.BuildConfig
import ru.practicum.android.diploma.core.config.ApiConfig
import ru.practicum.android.diploma.core.data.network.api.VacancyApi

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

}
