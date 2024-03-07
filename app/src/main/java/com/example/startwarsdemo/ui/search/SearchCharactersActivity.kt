package com.example.startwarsdemo.ui.search

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.startwarsdemo.R
import com.example.startwarsdemo.data.common.DataSourceException
import com.example.startwarsdemo.data.common.StarWarsResult
import com.example.startwarsdemo.data.common.onError
import com.example.startwarsdemo.data.common.onLoading
import com.example.startwarsdemo.data.common.onSuccess
import com.example.startwarsdemo.databinding.ActivitySearchCharactersBinding
import com.example.startwarsdemo.domain.models.CharacterModel
import com.example.startwarsdemo.ui.details.CharacterDetailsActivity
import com.example.startwarsdemo.utils.CHARACTER_EXTRA
import com.example.startwarsdemo.utils.ConnectionLiveData
import com.example.startwarsdemo.utils.getTextChangeStateFlow
import com.example.startwarsdemo.utils.hide
import com.example.startwarsdemo.utils.show
import com.example.startwarsdemo.utils.toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import okhttp3.ResponseBody

@AndroidEntryPoint
class SearchCharactersActivity : AppCompatActivity() {

    private val viewModel : SearchCharactersViewModel by viewModels()

    private lateinit var binding: ActivitySearchCharactersBinding
    private val linearLayoutManager by lazy { LinearLayoutManager(this) }
    private lateinit var charactersAdapter: CharactersAdapter


    @ExperimentalCoroutinesApi
    @FlowPreview
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchCharactersBinding.inflate(layoutInflater)
        setContentView(binding.root)
        checkInternetAvailability()
        setEditTextListener()
        initObserver()
    }

    private fun checkInternetAvailability() {
        ConnectionLiveData(this).observe(this) {
            if (!it) toast(getString(R.string.error_network))
        }
    }

    private fun initObserver() {
        lifecycleScope.launch {
            viewModel.resultListCharacters.collect  {
                it.onSuccess { list ->
                    setListAdapter(list)
                }.onError { error ->
                    binding.progressCircular.hide()
                    showError(error)
                    when (error.messageResource){
                        is Int -> setError(getString(error.messageResource))
                        is ResponseBody? -> setError(error.messageResource?.string())
                    }
                }.onLoading {
                    binding.groupError.hide()
                    binding.progressCircular.show()
                }
            }
        }

    }


    @FlowPreview
    @ExperimentalCoroutinesApi
    private fun setEditTextListener() {
        lifecycleScope.launch {
            binding.etSearch.getTextChangeStateFlow()
                .debounce(300)
                .filter { query ->
                    if (query.isEmpty()) {
                        runOnUiThread { setError(null) }
                        return@filter false
                    } else {
                        return@filter true
                    }
                }
                .distinctUntilChanged()
                .flatMapLatest { query ->
                    viewModel.searchCharacters(query)
                }
                .flowOn(Dispatchers.Default)
                .collect {result ->
                    processResult(result)
                }
        }

    }

    private fun processResult(result: StarWarsResult<List<CharacterModel>>) {
        result.onSuccess { list ->
            setListAdapter(list)
        }.onError { error ->
            binding.progressCircular.hide()
            showError(error)
            when (error.messageResource) {
                is Int -> setError(getString(error.messageResource))
                is ResponseBody? -> setError(error.messageResource?.string())
            }
        }.onLoading {
            binding.groupError.hide()
            binding.progressCircular.show()
        }
    }

    private fun setListAdapter(list: List<CharacterModel>) {
        binding.progressCircular.hide()
        if (list.isEmpty()) {
            binding.groupError.show()
            binding.rvCharacters.hide()
        } else {
            binding.rvCharacters.show()
            binding.groupError.hide()
            if (!::charactersAdapter.isInitialized) {
                with(binding.rvCharacters) {
                    layoutManager = linearLayoutManager
                    charactersAdapter = CharactersAdapter { setOnCharacterClicked(it) }
                    adapter = charactersAdapter
                    charactersAdapter.submitList(list)
                }
            } else {
                charactersAdapter.submitList(list)
            }
        }
    }

    private fun setOnCharacterClicked(characterModel: CharacterModel) {
        Intent(this, CharacterDetailsActivity::class.java).apply {
            putExtra(CHARACTER_EXTRA, characterModel)
            startActivity(this)
        }
    }

    private fun setError(error: String?) {
        binding.rvCharacters.hide()
        binding.progressCircular.hide()
        binding.groupError.show()
        binding.tvError.text = error
    }

    private fun showError(error: DataSourceException) {
        when (error.messageResource) {
            is Int -> toast(getString(error.messageResource))
            is ResponseBody? -> toast(error.messageResource!!.string())
        }
    }
}