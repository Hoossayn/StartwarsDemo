package com.example.startwarsdemo.viewModels.details

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.startwarsdemo.data.common.onError
import com.example.startwarsdemo.data.common.onSuccess
import com.example.startwarsdemo.data.response.MovieResponse
import com.example.startwarsdemo.datasource.StarWarsDataSourceImplTest
import com.example.startwarsdemo.domain.usecases.GetMoviesUseCase
import com.example.startwarsdemo.domain.usecases.GetPlanetUseCase
import com.example.startwarsdemo.domain.usecases.GetSpeciesUseCase
import com.example.startwarsdemo.helpers.MainCoroutineRule
import com.example.startwarsdemo.helpers.getJson
import com.example.startwarsdemo.repository.StarWarsRepositoryImplTest
import com.example.startwarsdemo.ui.details.CharactersDetailsViewModel
import com.example.startwarsdemo.utils.fromJsonToObjectType
import com.google.common.truth.Truth.assertThat
import com.google.gson.Gson
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class DetailsCharactersViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: CharactersDetailsViewModel

    @Before
    fun setup() {
        val repo = StarWarsRepositoryImplTest(StarWarsDataSourceImplTest())
        viewModel = CharactersDetailsViewModel(
            GetMoviesUseCase(repo),
            GetPlanetUseCase(repo),
            GetSpeciesUseCase(repo),
        )
    }

    @Test
    fun `getSpecie return species of the character`() {
        viewModel.getSpecies(listOf("https://swapi.dev/api/species/2/"))
        viewModel.resultSpecie.value.onSuccess { result ->
            assertThat(result).isNotNull()
            assertThat(result.first().name).matches("Mirialan")
        }.onError { error ->
            assertThat(error).isNull()
        }
    }

    @Test
    fun `getMovie return movies that the character has featured in`() {
        viewModel.getMovies(listOf("https://swapi.dev/api/films/2/"))
        val movieDesc =
            Gson().fromJsonToObjectType<MovieResponse?>(getJson("movie.json"))
        viewModel.resultMovie.value.onSuccess { result ->
            assertThat(result).isNotNull()
            assertThat(result.first().title).matches(movieDesc?.title)
            assertThat(result.first().description).matches(movieDesc?.description)
        }.onError { error ->
            assertThat(error).isNull()
        }
    }

    @Test
    fun `getPlanet return the planet the character is from`() {
        viewModel.getPlanet("https://swapi.dev/api/planets/3/")
        viewModel.resultPlanet.value.onSuccess { result ->
            assertThat(result).isNotNull()
            assertThat(result.population).matches("200000")
        }.onError { error ->
            assertThat(error).isNull()
        }
    }
}
