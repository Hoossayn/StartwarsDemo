package com.example.startwarsdemo.domain.usecases

import com.example.startwarsdemo.domain.repository.StarWarsRepository
import javax.inject.Inject

class GetPlanetUseCase @Inject constructor(
    private val starWarsRepository: StarWarsRepository,
) {
    suspend operator fun invoke(planetUrl: String) = starWarsRepository.getPlanet(planetUrl)
}
