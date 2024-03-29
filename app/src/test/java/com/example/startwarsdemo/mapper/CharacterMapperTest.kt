package com.example.startwarsdemo.mapper

import com.example.startwarsdemo.data.response.CharacterResponse
import com.example.startwarsdemo.domain.models.CharacterModel
import com.example.startwarsdemo.utils.UNDEFINED
import com.google.common.truth.Truth
import org.junit.Test

class CharacterMapperTest {
    private val characterResponse = CharacterResponse(
        name = "Luke Sky",
        birthYear = null,
        height = null,
        homeWorld = null,
        films = listOf(),
        species = listOf("url")

    )
    private val characterModel: CharacterModel = characterResponse.mapToDomainModel()

    @Test
    fun characterModelMapperTest() {
        Truth.assertThat(characterModel.name).matches("Luke Sky")
        Truth.assertThat(characterModel.height).isNotNull()
        Truth.assertThat(characterModel.birthYear).matches(UNDEFINED)
        Truth.assertThat(characterModel.homeWorld).isNotEmpty()
        Truth.assertThat(characterModel.films).hasSize(0)
        Truth.assertThat(characterModel.species.size).isAtLeast(1)
    }

}