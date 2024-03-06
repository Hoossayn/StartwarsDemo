package com.example.startwarsdemo.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CharacterModel(
        val name: String,
        val birthYear: String,
        val height: String,
        val homeWorld: String,
        val films: List<String>,
        val species: List<String>
) : Parcelable {
    fun getPlanetUrl() = homeWorld
    fun getMoviesUrl() = films.map { it }
    fun getSpeciesUrl() = species.map { it }
}
