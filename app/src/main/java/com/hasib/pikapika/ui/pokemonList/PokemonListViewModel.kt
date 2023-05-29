package com.hasib.pikapika.ui.pokemonList

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import androidx.palette.graphics.Palette
import com.hasib.pikapika.data.mappers.toPokemon
import com.hasib.pikapika.domain.Pokemon
import com.hasib.pikapika.repository.PokemonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val repository: PokemonRepository
) : ViewModel() {

    private val _searchString = MutableLiveData("")
    val searchString: LiveData<String> = _searchString

    val pokemonPagingFlow = repository.pokemonPager
        .flow
        .map { pagingData ->
            pagingData.map { it.toPokemon() }
        }
        .cachedIn(viewModelScope)

    var searchedPokemonPagingFlow = emptyFlow<PagingData<Pokemon>>()

    fun searchPokemon(string: String) {
        _searchString.value = string
        searchedPokemonPagingFlow = repository.searchPokemonPager(string)
            .flow
            .map { pagingData ->
                pagingData.map { it.toPokemon() }
            }
            .cachedIn(viewModelScope)
    }

    fun calculateDominantColor(drawable: Drawable, onFinish: (Color) -> Unit) {
        val bmp = (drawable as BitmapDrawable).bitmap.copy(Bitmap.Config.ARGB_8888, true)

        Palette.from(bmp).generate { palette ->
            palette?.dominantSwatch?.rgb?.let { colorValue ->
                onFinish(Color(colorValue))
            }
        }
    }
}