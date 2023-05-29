package com.hasib.pikapika.data.remote

import com.hasib.pikapika.data.remote.response.PokemonDetailsDto
import com.hasib.pikapika.data.remote.response.PokemonListDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonApi {

    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): PokemonListDto

    @GET("pokemon/{name}")
    suspend fun getPokemonInfo(
        @Path("name") name: String
    ): PokemonDetailsDto

    companion object {
        const val BASE_URL = "https://pokeapi.co/api/v2/"
    }
}