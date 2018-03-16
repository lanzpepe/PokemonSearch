package com.elano.pokemonsearch.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Pokemon(val id: Int, val name: String, val weight: Int, val sprites: Sprites, val height: Int,
                   val abilities: ArrayList<Abilities>, val stats: ArrayList<Stats>, val types: ArrayList<Types>) : Serializable

data class Abilities(val ability: Ability)

data class Types(val type: Type)

data class Ability(@SerializedName("name") val name: String)

data class Sprites(@SerializedName("front_default") val frontDefault: String)

data class Type(@SerializedName("name") val name: String)

data class Stats(@SerializedName("base_stat") val baseStat: Int)