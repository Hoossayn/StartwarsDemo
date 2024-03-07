package com.example.startwarsdemo.data.response

import com.example.startwarsdemo.domain.models.MovieModel
import com.example.startwarsdemo.utils.UNDEFINED
import com.google.gson.annotations.SerializedName

data class MovieResponse(
    @SerializedName("title") val title: String?,
    @SerializedName("opening_crawl") val description: String?,
) : DomainMapper<MovieModel> {
    override fun mapToDomainModel() = MovieModel(
        title ?: UNDEFINED,
        description
            ?: UNDEFINED,
        false,
    )
}
