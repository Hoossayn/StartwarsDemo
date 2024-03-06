package com.example.startwarsdemo.ui.search

import androidx.lifecycle.ViewModel
import com.example.startwarsdemo.data.common.StarWarsResult
import com.example.startwarsdemo.domain.models.CharacterModel
import com.example.startwarsdemo.domain.usecases.SearchCharactersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@HiltViewModel
class SearchCharactersViewModel @Inject constructor(
    private val searchCharactersUseCase: SearchCharactersUseCase
) : ViewModel() {

    private val _characters = MutableStateFlow<StarWarsResult<List<CharacterModel>>>(StarWarsResult.Loading)
    val resultListCharacters :StateFlow<StarWarsResult<List<CharacterModel>>> = _characters
     fun searchCharacters(input: String): Flow<StarWarsResult<List<CharacterModel>>> {
         return flow<StarWarsResult<List<CharacterModel>>> {
             searchCharactersUseCase(input).collect {
                 _characters.emit(it)
             }
         }.flowOn(Dispatchers.IO)
     }

}