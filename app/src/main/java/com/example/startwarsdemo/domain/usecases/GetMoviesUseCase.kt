package com.example.startwarsdemo.domain.usecases

import com.example.startwarsdemo.domain.repository.StarWarsRepository
import javax.inject.Inject

class GetMoviesUseCase @Inject constructor(
    private val starWarsRepository: StarWarsRepository,
) {
    suspend operator fun invoke(movieUrls: List<String>) = starWarsRepository.getMovies(movieUrls)
}
