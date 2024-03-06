package com.example.startwarsdemo.ui.details

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.startwarsdemo.R
import com.example.startwarsdemo.data.common.DataSourceException
import com.example.startwarsdemo.data.common.onError
import com.example.startwarsdemo.data.common.onLoading
import com.example.startwarsdemo.data.common.onSuccess
import com.example.startwarsdemo.databinding.ActivityCharacterDetailsBinding
import com.example.startwarsdemo.domain.models.CharacterModel
import com.example.startwarsdemo.utils.CHARACTER_EXTRA
import com.example.startwarsdemo.utils.UNDEFINED
import com.example.startwarsdemo.utils.convertCmToFeet
import com.example.startwarsdemo.utils.hasValue
import com.example.startwarsdemo.utils.hide
import com.example.startwarsdemo.utils.show
import com.example.startwarsdemo.utils.toast
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.ResponseBody

@AndroidEntryPoint
class CharacterDetailsActivity : AppCompatActivity() {

    private val viewModel : CharactersDetailsViewModel by viewModels()

    private lateinit var binding: ActivityCharacterDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCharacterDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initObservers()
        initViews()
    }

    private fun initViews() {
        getExtraCharacter()?.apply {
            with(binding) {
                tvDetailsNameValue.text = name
                tvDetailsBirthYearValue.text = birthYear
                if (height.hasValue()) {
                    tvDetailsHeightValue.text =
                        getString(R.string.height_in_cm_and_feet, height, height.convertCmToFeet())
                } else {
                    tvDetailsHeightValue.text = UNDEFINED
                }
            }
            getPlanet(getPlanetUrl())
            getMovies(this)
            getSpecies(this)
        }
    }

    private fun getPlanet(planet: String) {
        if (planet.hasValue()) viewModel.getPlanet(planet)
    }

    private fun getSpecies(character: CharacterModel) {
        if (character.species.isNotEmpty()) {
            viewModel.getSpecies(character.getSpeciesUrl())
        }
    }

    private fun getMovies(character: CharacterModel) {
        if (character.films.isNotEmpty()) {
            viewModel.getMovies(character.getMoviesUrl())
        }
    }

    private fun getExtraCharacter() =
        intent?.extras?.getParcelable(CHARACTER_EXTRA) as CharacterModel?


    private fun initObservers() {
        lifecycleScope.launchWhenStarted {
            viewModel.resultMovie.collect {
                it.onSuccess { movies ->
                    binding.progressCircular.hide()
                    binding.cardMovies.show()
                    with(binding.rvMovies) {
                        layoutManager = LinearLayoutManager(this@CharacterDetailsActivity)
                        adapter = MoviesAdapter(movies)
                    }

                }.onError { error ->
                    showError(error)
                    binding.progressCircular.show()
                }.onLoading {
                    binding.progressCircular.show()
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.resultPlanet.collect {
                it.onSuccess { planet ->
                    binding.cardPopulation.show()
                    binding.tvDetailsPopulation.text =
                        getString(
                            R.string.population_value,
                            getExtraCharacter()?.name,
                            planet.population
                        )
                    binding.progressCircular.hide()
                }.onError { error ->
                    showError(error)
                    binding.progressCircular.show()
                }.onLoading {
                    binding.progressCircular.show()
                }
            }
        }


        lifecycleScope.launchWhenStarted {
            viewModel.resultSpecie.collect {
                it.onSuccess { species ->
                    binding.progressCircular.hide()
                    binding.cardSpecies.show()
                    with(binding.rvSpecies) {
                        layoutManager = LinearLayoutManager(this@CharacterDetailsActivity)
                        adapter = SpeciesAdapter(species)
                    }

                }.onError { error ->
                    showError(error)
                    binding.progressCircular.show()
                }.onLoading {
                    binding.progressCircular.show()
                }
            }
        }
    }

    private fun showError(error: DataSourceException) {
        when (error.messageResource) {
            is Int -> toast(getString(error.messageResource))
            is ResponseBody? -> toast(error.messageResource!!.string())
        }
    }
}