package com.example.startwarsdemo.domain.repository


import com.example.startwarsdemo.data.common.StarWarsResult
import com.example.startwarsdemo.domain.models.CharacterModel
import com.example.startwarsdemo.domain.models.MovieModel
import com.example.startwarsdemo.domain.models.PlanetModel
import com.example.startwarsdemo.domain.models.SpecieModel
import kotlinx.coroutines.flow.Flow

interface StarWarsRepository {

    suspend fun searchCharacters(input: String): Flow<StarWarsResult<List<CharacterModel>>>
    suspend fun getPlanet(planetUrl: String): Flow<StarWarsResult<PlanetModel>>
    suspend fun getSpecies(specieUrl: List<String>): Flow<StarWarsResult<List<SpecieModel>>>
    suspend fun getMovies(movieUrl: List<String>): Flow<StarWarsResult<List<MovieModel>>>
}
