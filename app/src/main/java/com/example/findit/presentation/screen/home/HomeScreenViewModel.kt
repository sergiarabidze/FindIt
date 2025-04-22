package com.example.findit.presentation.screen.home

import android.util.Log.d
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.findit.domain.resource.Resource
import com.example.findit.domain.usecase.GetPostsUseCase
import com.example.findit.domain.usecase.SearchPostsUseCase
import com.example.findit.presentation.mappper.toPresentation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val getPostsUseCase: GetPostsUseCase,
    private val searchPostsUseCase: SearchPostsUseCase
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")

    private val _state = MutableStateFlow(HomeScreenUiState())
    val state = _state.asStateFlow()

    private val _effect = MutableSharedFlow<HomeScreenEffect>()
   val effect = _effect.asSharedFlow()

    init {
        observeQueryChanges()
    }

    fun onEvent(homeScreenEvent: HomeScreenEvent) {
        when (homeScreenEvent) {
            is HomeScreenEvent.OnPostClicked -> {
                viewModelScope.launch {
                    _effect.emit(HomeScreenEffect.NavigateToPostInfo(homeScreenEvent.postId))
                }
            }
            is HomeScreenEvent.OnSearchQueryChanged -> {
                _searchQuery.value = homeScreenEvent.query
            }
            is HomeScreenEvent.ClearError -> {
                clearErrors()
            }
        }
    }

    private fun loadPosts() {
        viewModelScope.launch {
            getPostsUseCase().collectLatest { result ->
                when (result) {
                    is Resource.Loader -> _state.value =
                        _state.value.copy(isLoading = result.isLoading)

                    is Resource.Success -> {
                        _state.value =
                            _state.value.copy(posts = result.data.map { it.toPresentation() },isLoading = false)
                    }

                    is Resource.Error -> {
                        _state.value = state.value.copy(error = result.errorMessage,isLoading = false)
                    }

                }

            }

        }
    }

    @OptIn(FlowPreview::class)
    private fun observeQueryChanges() {
        viewModelScope.launch {
            _searchQuery
                .debounce(500)
                .distinctUntilChanged()
                .collect { query ->
                    if (query.isNotBlank()) {
                        searchQuery(query)
                    } else {
                        loadPosts()
                    }
                }
        }
    }

    private fun searchQuery(query: String) {
        viewModelScope.launch {
            searchPostsUseCase(query).collect { result ->
                when (result) {
                    is Resource.Loader -> _state.value =
                        _state.value.copy(isLoading = result.isLoading)
                    is Resource.Success -> {
                        _state.value =
                            _state.value.copy(posts = result.data.map { it.toPresentation() },isLoading = false)
                    }
                    is Resource.Error -> {
                        _state.value = state.value.copy(error = result.errorMessage,isLoading = false)
                    }
                }
            }
        }
    }
    private fun clearErrors(){
        _state.value = _state.value.copy(error = null)
    }
}