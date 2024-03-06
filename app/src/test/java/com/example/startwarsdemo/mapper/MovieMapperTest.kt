package com.example.startwarsdemo.mapper

import com.example.startwarsdemo.data.response.MovieResponse
import com.example.startwarsdemo.utils.UNDEFINED
import com.google.common.truth.Truth
import org.junit.Test

class MovieMapperTest {
    private val movieResponse = MovieResponse(
        title = "The last one",
        description = ""

    )
    private val movieModel = movieResponse.mapToDomainModel()

    @Test
    fun movieModelMapperTest() {
        Truth.assertThat(movieModel.title).matches("Luke Sky")
        Truth.assertThat(movieModel.description).matches(UNDEFINED)
    }

}