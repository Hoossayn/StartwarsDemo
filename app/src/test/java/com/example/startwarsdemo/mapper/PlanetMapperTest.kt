package com.example.startwarsdemo.mapper

import com.example.startwarsdemo.data.response.PlanetResponse
import com.google.common.truth.Truth
import org.junit.Test

class PlanetMapperTest {
    private val planetResponse = PlanetResponse(
        population = "120000"
    )
    private val planetModel = planetResponse.mapToDomainModel()

    @Test
    fun planetModelMapperTest() {
        Truth.assertThat(planetModel.population).matches("12000")
    }

}