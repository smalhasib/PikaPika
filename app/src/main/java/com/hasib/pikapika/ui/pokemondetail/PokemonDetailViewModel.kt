package com.hasib.pikapika.ui.pokemondetail

import androidx.lifecycle.ViewModel
import com.hasib.pikapika.data.remote.response.PokemonDetailsDto
import com.hasib.pikapika.repository.PokemonRepository
import com.hasib.pikapika.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PokemonDetailViewModel @Inject constructor(
    private val repository: PokemonRepository
) : ViewModel() {
    suspend fun getPokemonInfo(pokemonName: String): Resource<PokemonDetailsDto> {
        return repository.getPokemonInfo(pokemonName)
    }
}