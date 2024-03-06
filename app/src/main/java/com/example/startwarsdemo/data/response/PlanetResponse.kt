package com.example.startwarsdemo.data.response

import com.example.startwarsdemo.domain.models.PlanetModel
import com.example.startwarsdemo.utils.UNDEFINED
import com.google.gson.annotations.SerializedName


data class PlanetResponse(
        @SerializedName("population") val population: String?
) : DomainMapper<PlanetModel> {
    override fun mapToDomainModel() = PlanetModel(population ?: UNDEFINED)
}
