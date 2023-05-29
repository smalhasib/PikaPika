package com.hasib.pikapika.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class PokemonListItemEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val pokemonName: String,
    val imageUrl: String,
    val number: Int
)