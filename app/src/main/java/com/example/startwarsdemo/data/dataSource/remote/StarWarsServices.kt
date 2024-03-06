package com.example.startwarsdemo.data.dataSource.remote

import com.example.startwarsdemo.data.common.GET_SEARCH_CHARACTERS_URL
import com.example.startwarsdemo.data.response.ListCharacterResponse
import com.example.startwarsdemo.data.response.MovieResponse
import com.example.startwarsdemo.data.response.PlanetResponse
import com.example.startwarsdemo.data.response.SpecieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface StarWarsServices {

    @GET(GET_SEARCH_CHARACTERS_URL)
    suspend fun searchCharacters(@Query("search") input: String): Response<ListCharacterResponse>

    @GET
    suspend fun getPlanet(@Url planetUrl: String): Response<PlanetResponse>

    @GET
    suspend fun getSpecie(@Url specieUrl: String): Response<SpecieResponse>

    @GET
    suspend fun getMovie(@Url movieUrl: String): Response<MovieResponse>
}
