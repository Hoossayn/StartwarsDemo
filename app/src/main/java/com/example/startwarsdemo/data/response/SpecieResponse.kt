package com.example.startwarsdemo.data.response

import com.example.startwarsdemo.domain.models.SpecieModel
import com.example.startwarsdemo.utils.UNDEFINED
import com.google.gson.annotations.SerializedName


data class SpecieResponse(
        @SerializedName("name") val name: String?,
        @SerializedName("language") val language: String?
) : DomainMapper<SpecieModel> {
    override fun mapToDomainModel() = SpecieModel(name ?: UNDEFINED, language ?: UNDEFINED)
}
