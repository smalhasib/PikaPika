package com.hasib.pikapika.di

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import androidx.room.Room
import com.hasib.pikapika.data.local.PokemonDatabase
import com.hasib.pikapika.data.remote.PokemonApi
import com.hasib.pikapika.data.remote.PokemonApi.Companion.BASE_URL
import com.hasib.pikapika.repository.PokemonRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@OptIn(ExperimentalPagingApi::class)
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun providePokemonRepository(
        api: PokemonApi,
        database: PokemonDatabase
    ) = PokemonRepository(api, database)

    @Singleton
    @Provides
    fun providePokemonApi(): PokemonApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(PokemonApi::class.java)
    }

    @Provides
    @Singleton
    fun providePokemonDatabase(@ApplicationContext context: Context): PokemonDatabase {
        return Room.databaseBuilder(
            context,
            PokemonDatabase::class.java,
            "pokemon.db"
        ).build()
    }
}