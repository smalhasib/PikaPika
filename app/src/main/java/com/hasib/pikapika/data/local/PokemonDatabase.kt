package com.hasib.pikapika.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [PokemonListItemEntity::class],
    version = 1,
    exportSchema = false
)
abstract class PokemonDatabase : RoomDatabase() {

    abstract val dao: PokemonDao
}