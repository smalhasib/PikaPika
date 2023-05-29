package com.hasib.pikapika.data.remote

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.hasib.pikapika.data.local.PokemonDatabase
import com.hasib.pikapika.data.local.PokemonListItemEntity
import com.hasib.pikapika.data.mappers.toPokemonEntity
import okio.IOException
import retrofit2.HttpException

@OptIn(ExperimentalPagingApi::class)
class PokemonRemoteMediator(
    private val pokemonDatabase: PokemonDatabase,
    private val pokemonApi: PokemonApi
) : RemoteMediator<Int, PokemonListItemEntity>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, PokemonListItemEntity>
    ): MediatorResult {
        return try {
            val pageSize = state.config.pageSize
            val loadKey = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return MediatorResult.Success(
                    endOfPaginationReached = true
                )

                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                    if (lastItem == null) {
                        1
                    } else {
                        (lastItem.id / pageSize) + 1
                    }
                }
            }

            val pokemonList = pokemonApi.getPokemonList(pageSize, loadKey * pageSize)

            pokemonDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    pokemonDatabase.dao.clearPokemonList()
                }
                pokemonDatabase.dao.upsertAll(pokemonList.toPokemonEntity())
            }

            MediatorResult.Success(
                endOfPaginationReached = pokemonList.results.isEmpty()
            )
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }
}