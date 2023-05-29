@file:OptIn(ExperimentalMaterial3Api::class)

package com.hasib.pikapika.ui.pokemonList

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.TopStart
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.hasib.pikapika.R
import com.hasib.pikapika.domain.Pokemon
import com.hasib.pikapika.ui.theme.RobotoCondensed

@Composable
fun PokemonListScreen(
    navController: NavController,
    viewModel: PokemonListViewModel = hiltViewModel()
) {
    val searchString: String by viewModel.searchString.observeAsState(initial = "")

    Surface(color = MaterialTheme.colorScheme.background, modifier = Modifier.fillMaxSize()) {
        Column {
            Spacer(modifier = Modifier.height(20.dp))
            Image(
                painter = painterResource(id = R.drawable.ic_international_pok_mon_logo),
                contentDescription = "Pokemon",
                modifier = Modifier
                    .fillMaxWidth()
                    .align(CenterHorizontally)
            )
            SearchBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                searchString = searchString
            ) {
                viewModel.searchPokemon(it)
            }
            Spacer(modifier = Modifier.height(16.dp))
            PokemonList(
                navController = navController,
                pokemons = if (searchString.isEmpty()) {
                    viewModel.pokemonPagingFlow.collectAsLazyPagingItems()
                } else {
                    viewModel.searchedPokemonPagingFlow.collectAsLazyPagingItems()
                }
            )
        }
    }
}

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    searchString: String,
    onSearch: (String) -> Unit = {}
) {


    Box(modifier = modifier) {
        TextField(
            value = searchString,
            onValueChange = {
                onSearch(it)
            },
            maxLines = 1,
            label = { Text(text = "Search...") },
            singleLine = true,
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                containerColor = MaterialTheme.colorScheme.surface
            ),
            textStyle = TextStyle(color = Color.Black),
            modifier = Modifier
                .fillMaxWidth()
                .shadow(5.dp, CircleShape)
                .background(Color.White, CircleShape)
                .padding(horizontal = 20.dp)
        )
    }
}

@Composable
fun PokemonList(
    navController: NavController,
    pokemons: LazyPagingItems<Pokemon>
) {
    val context = LocalContext.current
    LaunchedEffect(key1 = pokemons.loadState) {
        if (pokemons.loadState.refresh is LoadState.Error) {
            Toast.makeText(
                context,
                "Error: ${(pokemons.loadState.refresh as LoadState.Error).error.message}",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    Box(contentAlignment = TopStart, modifier = Modifier.fillMaxSize()) {
        if (pokemons.loadState.refresh is LoadState.Loading) {
            CircularProgressIndicator(modifier = Modifier.align(Center))
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(16.dp)
            ) {
                items(
                    count = pokemons.itemCount,
                    key = pokemons.itemKey(),
                    contentType = pokemons.itemContentType()
                ) {
                    pokemons[it]?.let { pokemon ->
                        PokemonEntry(
                            pokemon = pokemon,
                            navController = navController
                        )
                    }
                }
                item {
                    if (pokemons.loadState.append is LoadState.Loading) {
                        CircularProgressIndicator()
                    }
                }
            }
        }
    }
}

@Composable
fun PokemonEntry(
    pokemon: Pokemon,
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: PokemonListViewModel = hiltViewModel()
) {
    val defaultDominantColor = MaterialTheme.colorScheme.surface
    var dominantColor by remember {
        mutableStateOf(defaultDominantColor)
    }

    Box(
        modifier = modifier
            .padding(8.dp)
            .shadow(5.dp, RoundedCornerShape(10.dp))
            .clip(RoundedCornerShape(10.dp))
            .aspectRatio(1f)
            .background(
                Brush.verticalGradient(
                    listOf(dominantColor, defaultDominantColor)
                )
            )
            .clickable {
                navController.navigate("pokemon_detail_screen/${dominantColor.toArgb()}/${pokemon.pokemonName}")
            },
        contentAlignment = Center
    ) {
        Column {
            SubcomposeAsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(pokemon.imageUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = pokemon.pokemonName,
                modifier = Modifier
                    .size(120.dp)
                    .align(CenterHorizontally),
                loading = {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.scale(0.5f)
                    )
                },
                onSuccess = { success ->
                    viewModel.calculateDominantColor(success.result.drawable) { color ->
                        dominantColor = color
                    }
                }
            )
            Text(
                text = pokemon.pokemonName,
                fontFamily = RobotoCondensed,
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}