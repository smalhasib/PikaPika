package com.hasib.pikapika.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface PokemonDao {

    @Upsert
    suspend fun upsertAll(pokemonListItems: List<PokemonListItemEntity>)

    @Query("SELECT * FROM PokemonListItemEntity")
    fun pokemonListPagingSource(): PagingSource<Int, PokemonListItemEntity>

    @Query("SELECT * FROM PokemonListItemEntity WHERE pokemonName LIKE '%' || :name || '%'")
    fun searchInPokemonListPagingSource(name: String): PagingSource<Int, PokemonListItemEntity>

    @Query("DELETE FROM PokemonListItemEntity")
    suspend fun clearPokemonList()
}