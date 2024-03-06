package com.example.startwarsdemo.viewModels.search

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.startwarsdemo.data.common.onError
import com.example.startwarsdemo.data.common.onSuccess
import com.example.startwarsdemo.domain.usecases.SearchCharactersUseCase
import com.example.startwarsdemo.ui.search.SearchCharactersViewModel
import com.google.common.truth.Truth.assertThat
import com.example.startwarsdemo.datasource.StarWarsDataSourceImplTest
import com.example.startwarsdemo.helpers.MainCoroutineRule
import com.example.startwarsdemo.helpers.runBlockingTest
import com.example.startwarsdemo.repository.StarWarsRepositoryImplTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class SearchCharactersViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: SearchCharactersViewModel

    @Before
    fun setup() {
        viewModel = SearchCharactersViewModel(
            SearchCharactersUseCase(
                StarWarsRepositoryImplTest(
                    StarWarsDataSourceImplTest()
                )
            )
        )
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `get list characters , returns success`() = mainCoroutineRule.runBlockingTest {
        viewModel.searchCharacters("lu")
        viewModel.resultListCharacters.value.onSuccess { result ->
            assertThat(result).isNotNull()
            assertThat(result[0].name).isEqualTo("Luke Skywalker")
            assertThat(result.size).isAtLeast(2)
            assertThat(result[1].height).matches("170")
        }.onError { error ->
            assertThat(error).isNull()
        }

    }
}