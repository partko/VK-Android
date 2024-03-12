package com.example.androidvk.di

import com.example.androidvk.remote.AndroidVKRepository
import com.example.androidvk.remote.DummyjsonApi
import com.example.androidvk.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun providePokemonRepository(
        api: DummyjsonApi
    ) = AndroidVKRepository(api)

    @Singleton @Provides
    fun provideDummyisonApi(): DummyjsonApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(DummyjsonApi::class.java)
    }
}