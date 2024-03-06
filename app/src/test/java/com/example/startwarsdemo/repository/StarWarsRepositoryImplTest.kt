package com.example.startwarsdemo.repository


import com.example.startwarsdemo.data.common.StarWarsResult
import com.example.startwarsdemo.datasource.StarWarsDataSourceImplTest
import com.example.startwarsdemo.domain.models.CharacterModel
import com.example.startwarsdemo.domain.models.MovieModel
import com.example.startwarsdemo.domain.models.PlanetModel
import com.example.startwarsdemo.domain.models.SpecieModel
import com.example.startwarsdemo.domain.repository.StarWarsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart

class StarWarsRepositoryImplTest(private val starWarsDataSourceImplTest: StarWarsDataSourceImplTest) :
    StarWarsRepository {
    override suspend fun searchCharacters(input: String): Flow<StarWarsResult<List<CharacterModel>>> {
        return flow {
            starWarsDataSourceImplTest.searchCharacters(input).run {
                when (this) {
                    is StarWarsResult.Success -> {
                        data?.let { emit(StarWarsResult.Success(it.map { character -> character.mapToDomainModel() })) }
                    }
                    is StarWarsResult.Error -> {
                        emit(StarWarsResult.Error(exception))
                    }

                    else -> {}
                }
            }
        }.onStart { emit(StarWarsResult.Loading) }
    }

    override suspend fun getPlanet(planetUrl: String): Flow<StarWarsResult<PlanetModel>> {
        return flow {
            starWarsDataSourceImplTest.getPlanet(planetUrl).run {
                when (this) {
                    is StarWarsResult.Success -> {
                        data?.let { emit(StarWarsResult.Success(it.mapToDomainModel())) }
                    }
                    is StarWarsResult.Error -> {
                        emit(StarWarsResult.Error(exception))
                    }

                    else -> {}
                }
            }
        }.onStart { emit(StarWarsResult.Loading) }
    }

    override suspend fun getSpecies(specieUrl: List<String>): Flow<StarWarsResult<List<SpecieModel>>> {
        return flow<StarWarsResult<List<SpecieModel>>> {
            val resultList = mutableListOf<SpecieModel>()

            starWarsDataSourceImplTest.getSpecies(specieUrl).run {
                when (this) {
                    is StarWarsResult.Success -> {

                        data.let { responseList ->
                            responseList.forEach {
                            if (it != null) {
                                resultList.addAll(listOf(it.mapToDomainModel()))
                            }
                        } }

                         emit(StarWarsResult.Success(resultList))
                    }
                    is StarWarsResult.Error -> {
                        emit(StarWarsResult.Error(exception))
                    }

                    else -> {}
                }
            }
        }.onStart { emit(StarWarsResult.Loading) }
    }

    override suspend fun getMovies(movieUrl: List<String>): Flow<StarWarsResult<List<MovieModel>>> {
        return flow<StarWarsResult<List<MovieModel>>> {
            val resultList = mutableListOf<MovieModel>()

            starWarsDataSourceImplTest.getMovies(movieUrl).run {
                when (this) {
                    is StarWarsResult.Success -> {
                        data.let { it ->
                            it.forEach {
                            if (it != null) {
                                resultList.addAll(listOf(it.mapToDomainModel()))
                               // it.mapToDomainModel()
                            }
                        } }

                        emit(StarWarsResult.Success(resultList))
                    }
                    is StarWarsResult.Error -> {
                        emit(StarWarsResult.Error(exception))
                    }

                    else -> {}
                }
            }
        }.onStart { emit(StarWarsResult.Loading) }
    }

}