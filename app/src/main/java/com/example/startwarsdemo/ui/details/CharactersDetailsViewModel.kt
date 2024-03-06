package com.example.startwarsdemo.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.startwarsdemo.data.common.StarWarsResult
import com.example.startwarsdemo.domain.models.MovieModel
import com.example.startwarsdemo.domain.models.PlanetModel
import com.example.startwarsdemo.domain.models.SpecieModel
import com.example.startwarsdemo.domain.usecases.GetMoviesUseCase
import com.example.startwarsdemo.domain.usecases.GetPlanetUseCase
import com.example.startwarsdemo.domain.usecases.GetSpeciesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharactersDetailsViewModel @Inject constructor(
    private val getMovieUseCase: GetMoviesUseCase,
    private val getPlanetUseCase: GetPlanetUseCase,
    private val getSpecieUseCase: GetSpeciesUseCase
) : ViewModel() {

    private val _resultMovie = MutableStateFlow<StarWarsResult<List<MovieModel>>>(StarWarsResult.Loading)
    val resultMovie: StateFlow<StarWarsResult<List<MovieModel>>> = _resultMovie

    fun getMovies(movieUrls: List<String>) {
        viewModelScope.launch {
            getMovieUseCase(movieUrls).collect {
                _resultMovie.emit(it)
            }
        }
    }

    private val _resultSpecie = MutableStateFlow<StarWarsResult<List<SpecieModel>>>(StarWarsResult.Loading)
    val resultSpecie: StateFlow<StarWarsResult<List<SpecieModel>>> = _resultSpecie

    fun getSpecies(movieUrls: List<String>) {
        viewModelScope.launch {
            getSpecieUseCase(movieUrls).collect {
                _resultSpecie.emit(it)
            }
        }
    }

    private val _resultPlanet = MutableStateFlow<StarWarsResult<PlanetModel>>(StarWarsResult.Loading)
    val resultPlanet: StateFlow<StarWarsResult<PlanetModel>> = _resultPlanet

    fun getPlanet(movieUrl: String) {
        viewModelScope.launch {
            getPlanetUseCase(movieUrl).collect {
                _resultPlanet.emit(it)
            }
        }
    }
}