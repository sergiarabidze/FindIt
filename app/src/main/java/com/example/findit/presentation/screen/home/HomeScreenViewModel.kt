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
import com.example.findit.presentation.extension.*

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
            is HomeScreenEvent.OnFiltersSelected -> {
                _state.value = _state.value.copy(selectedFilters = homeScreenEvent.selectedFilters)
                applyFilters() // call a new function
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


    private fun applyFilters() {
        viewModelScope.launch {
            val filters = _state.value.selectedFilters

            getPostsUseCase().collectLatest { result ->
                when (result) {
                    is Resource.Loader -> _state.value = _state.value.copy(isLoading = result.isLoading)

                    is Resource.Success -> {
                        var posts = result.data.map { it.toPresentation() }

                        if (filters.isNotEmpty()) {
                            posts = posts.filter { post ->
                                filters.any { filter ->
                                    when (filter) {
                                        "today" -> post.isToday()
                                        "week" -> post.isThisWeek()
                                        "month" -> post.isThisMonth()
                                        "lost" -> post.isLost()
                                        "found" -> post.isFound()
                                        else -> true
                                    }
                                }
                            }
                        }
                        _state.value = _state.value.copy(posts = posts, isLoading = false)
                    }

                    is Resource.Error -> {
                        _state.value = _state.value.copy(error = result.errorMessage, isLoading = false)
                    }
                }
            }
        }
    }

    private fun clearErrors(){
        _state.value = _state.value.copy(error = null)
    }
}