package com.example.startwarsdemo.datasource

import com.example.startwarsdemo.data.common.DataSourceException
import com.example.startwarsdemo.data.common.StarWarsResult
import com.example.startwarsdemo.data.dataSource.remote.StarWarsDataSource
import com.example.startwarsdemo.data.response.CharacterResponse
import com.example.startwarsdemo.data.response.ListCharacterResponse
import com.example.startwarsdemo.data.response.PlanetResponse
import com.example.startwarsdemo.data.response.SpecieResponse
import com.example.startwarsdemo.utils.fromJsonToObjectType
import com.google.gson.Gson
import com.example.startwarsdemo.R
import com.example.startwarsdemo.data.response.MovieResponse
import com.example.startwarsdemo.helpers.getJson

class StarWarsDataSourceImplTest : StarWarsDataSource {

    override suspend fun searchCharacters(input: String): StarWarsResult<List<CharacterResponse>?> {
        val result =
            Gson().fromJsonToObjectType<ListCharacterResponse?>(getJson("list_characters.json"))
        return if (result != null) {
            StarWarsResult.Success(result.results)
        } else {
            StarWarsResult.Error(DataSourceException.Unexpected(R.string.error_unexpected_message))
        }
    }

    override suspend fun getPlanet(planetUrl: String): StarWarsResult<PlanetResponse?> {
        val result =
            Gson().fromJsonToObjectType<PlanetResponse?>(getJson("planet.json"))
        return if (result != null) {
            StarWarsResult.Success(result)
        } else {
            StarWarsResult.Error(DataSourceException.Unexpected(R.string.error_unexpected_message))
        }
    }

    override suspend fun getSpecies(specieUrls: List<String>): StarWarsResult<List<SpecieResponse?>> {
        val result =
            Gson().fromJsonToObjectType<SpecieResponse?>(getJson("specie.json"))
        return if (result != null) {
            StarWarsResult.Success(listOf(result))
        } else {
            StarWarsResult.Error(DataSourceException.Unexpected(R.string.error_unexpected_message))
        }
    }

    override suspend fun getMovies(movieUrls: List<String>): StarWarsResult<List<MovieResponse?>> {
        val result =
            Gson().fromJsonToObjectType<MovieResponse?>(getJson("movie.json"))
        return if (result != null) {
            StarWarsResult.Success(listOf(result))
        } else {
            StarWarsResult.Error(DataSourceException.Unexpected(R.string.error_unexpected_message))
        }
    }


}