package com.hasib.pikapika.data.remote.response

data class PokemonListDto(
    val count: Int,
    val next: String,
    val previous: Any,
    val results: List<Result>
)