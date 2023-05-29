package com.hasib.pikapika.data.mappers

import com.hasib.pikapika.data.local.PokemonListItemEntity
import com.hasib.pikapika.data.remote.response.PokemonListDto
import com.hasib.pikapika.domain.Pokemon

fun PokemonListDto.toPokemonEntity(): List<PokemonListItemEntity> {
    return results.map { entry ->
        val number = if (entry.url.endsWith("/")) {
            entry.url.dropLast(1).takeLastWhile { it.isDigit() }
        } else {
            entry.url.takeLastWhile { it.isDigit() }
        }
        val url =
            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$number.png"
        PokemonListItemEntity(pokemonName = entry.name, imageUrl = url, number = number.toInt())
    }.toList()
}

fun PokemonListItemEntity.toPokemon(): Pokemon = Pokemon(pokemonName, imageUrl, number)