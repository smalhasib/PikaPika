package com.hasib.pikapika.util

import androidx.compose.ui.graphics.Color
import com.hasib.pikapika.data.remote.response.Stat
import com.hasib.pikapika.data.remote.response.Type
import com.hasib.pikapika.ui.theme.AtkColor
import com.hasib.pikapika.ui.theme.DefColor
import com.hasib.pikapika.ui.theme.HPColor
import com.hasib.pikapika.ui.theme.SpAtkColor
import com.hasib.pikapika.ui.theme.SpDefColor
import com.hasib.pikapika.ui.theme.SpdColor
import com.hasib.pikapika.ui.theme.TypeBug
import com.hasib.pikapika.ui.theme.TypeDark
import com.hasib.pikapika.ui.theme.TypeDragon
import com.hasib.pikapika.ui.theme.TypeElectric
import com.hasib.pikapika.ui.theme.TypeFairy
import com.hasib.pikapika.ui.theme.TypeFighting
import com.hasib.pikapika.ui.theme.TypeFire
import com.hasib.pikapika.ui.theme.TypeFlying
import com.hasib.pikapika.ui.theme.TypeGhost
import com.hasib.pikapika.ui.theme.TypeGrass
import com.hasib.pikapika.ui.theme.TypeGround
import com.hasib.pikapika.ui.theme.TypeIce
import com.hasib.pikapika.ui.theme.TypeNormal
import com.hasib.pikapika.ui.theme.TypePoison
import com.hasib.pikapika.ui.theme.TypePsychic
import com.hasib.pikapika.ui.theme.TypeRock
import com.hasib.pikapika.ui.theme.TypeSteel
import com.hasib.pikapika.ui.theme.TypeWater
import java.util.Locale

fun parseTypeToColor(type: Type): Color {
    return when (type.type.name.lowercase(Locale.ROOT)) {
        "normal" -> TypeNormal
        "fire" -> TypeFire
        "water" -> TypeWater
        "electric" -> TypeElectric
        "grass" -> TypeGrass
        "ice" -> TypeIce
        "fighting" -> TypeFighting
        "poison" -> TypePoison
        "ground" -> TypeGround
        "flying" -> TypeFlying
        "psychic" -> TypePsychic
        "bug" -> TypeBug
        "rock" -> TypeRock
        "ghost" -> TypeGhost
        "dragon" -> TypeDragon
        "dark" -> TypeDark
        "steel" -> TypeSteel
        "fairy" -> TypeFairy
        else -> Color.Black
    }
}

fun parseStatToColor(stat: Stat): Color {
    return when (stat.stat.name.lowercase(Locale.ROOT)) {
        "hp" -> HPColor
        "attack" -> AtkColor
        "defense" -> DefColor
        "special-attack" -> SpAtkColor
        "special-defense" -> SpDefColor
        "speed" -> SpdColor
        else -> Color.White
    }
}

fun parseStatToAbbr(stat: Stat): String {
    return when (stat.stat.name.lowercase(Locale.ROOT)) {
        "hp" -> "HP"
        "attack" -> "Atk"
        "defense" -> "Def"
        "special-attack" -> "SpAtk"
        "special-defense" -> "SpDef"
        "speed" -> "Spd"
        else -> ""
    }
}