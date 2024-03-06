package com.example.startwarsdemo.domain.usecases

import com.example.startwarsdemo.domain.repository.StarWarsRepository
import javax.inject.Inject

open class GetSpeciesUseCase @Inject constructor(
        private val starWarsRepository: StarWarsRepository
) {
    suspend operator fun invoke(specieUrl: List<String>) = starWarsRepository.getSpecies(specieUrl)
}
