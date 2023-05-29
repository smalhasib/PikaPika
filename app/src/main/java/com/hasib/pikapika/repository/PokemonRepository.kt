package com.hasib.pikapika.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.hasib.pikapika.data.local.PokemonDatabase
import com.hasib.pikapika.data.local.PokemonListItemEntity
import com.hasib.pikapika.data.remote.PokemonApi
import com.hasib.pikapika.data.remote.PokemonRemoteMediator
import com.hasib.pikapika.data.remote.response.PokemonDetailsDto
import com.hasib.pikapika.util.Resource
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class PokemonRepository @Inject constructor(
    private val api: PokemonApi,
    private val database: PokemonDatabase
) {
    suspend fun getPokemonInfo(pokemonName: String): Resource<PokemonDetailsDto> {
        val response = try {
            api.getPokemonInfo(pokemonName)
        } catch (e: Exception) {
            return Resource.Error("An unknown error occurred")
        }
        return Resource.Success(response)
    }

    @OptIn(ExperimentalPagingApi::class)
    val pokemonPager: Pager<Int, PokemonListItemEntity> = Pager(
        config = PagingConfig(pageSize = 20),
        remoteMediator = PokemonRemoteMediator(database, api),
        pagingSourceFactory = {
            database.dao.pokemonListPagingSource()
        }
    )

    fun searchPokemonPager(str: String): Pager<Int, PokemonListItemEntity> = Pager(
        config = PagingConfig(pageSize = 20),
        pagingSourceFactory = {
            database.dao.searchInPokemonListPagingSource(str)
        }
    )
}