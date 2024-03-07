package com.example.startwarsdemo.data.dataSource.remote

import com.example.startwarsdemo.data.common.StarWarsResult
import com.example.startwarsdemo.data.response.CharacterResponse
import com.example.startwarsdemo.data.response.MovieResponse
import com.example.startwarsdemo.data.response.PlanetResponse
import com.example.startwarsdemo.data.response.SpecieResponse

interface StarWarsDataSource {
    suspend fun searchCharacters(input: String): StarWarsResult<List<CharacterResponse>?>
    suspend fun getPlanet(planetUrl: String): StarWarsResult<PlanetResponse?>
    suspend fun getSpecies(specieUrls: List<String>): StarWarsResult<List<SpecieResponse?>>
    suspend fun getMovies(movieUrls: List<String>): StarWarsResult<List<MovieResponse?>>
}
