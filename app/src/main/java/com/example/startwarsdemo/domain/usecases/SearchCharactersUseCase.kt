package com.example.startwarsdemo.domain.usecases

import com.example.startwarsdemo.domain.repository.StarWarsRepository
import javax.inject.Inject

class SearchCharactersUseCase @Inject constructor(
    private val starWarsRepository: StarWarsRepository,
) {
    suspend operator fun invoke(input: String) = starWarsRepository.searchCharacters(input)
}
