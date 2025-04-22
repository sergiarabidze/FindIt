package com.example.findit.presentation.screen.post

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.findit.domain.resource.Resource
import com.example.findit.domain.usecase.GetPostByIdUseCase
import com.example.findit.domain.usecase.GetProfileUseCase
import com.example.findit.presentation.mappper.toPresentation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(
    private val getPostByIdUseCase: GetPostByIdUseCase,
    private val getUserProfileUseCase: GetProfileUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(PostState())
    val state = _state.asStateFlow()

    fun onEvent(event: PostEvent) {
        when (event) {
            is PostEvent.LoadPost -> loadPost(event.postId)
            PostEvent.ClearError -> clearError()
        }
    }

    private fun loadPost(postId: String) {
        viewModelScope.launch {
            getPostByIdUseCase(postId).collect { result ->
                when (result) {
                    is Resource.Error -> {
                        _state.value =
                            _state.value.copy(error = result.errorMessage, isLoading = false)
                    }

                    is Resource.Loader -> {
                        _state.value = _state.value.copy(isLoading = result.isLoading)
                    }

                    is Resource.Success -> {
                        _state.value = _state.value.copy(post = result.data.toPresentation(), isLoading = false)
                        getUserProfileUseCase.invoke().collect { userResult ->
                            when (userResult) {
                                is Resource.Error -> {
                                    _state.value =
                                        _state.value.copy(error = userResult.errorMessage, isLoading = false)
                                }
                                is Resource.Loader -> {
                                    _state.value = _state.value.copy(isLoading = userResult.isLoading)
                                }
                                is Resource.Success -> {
                                    _state.value = _state.value.copy(user = userResult.data, isLoading = false)
                                }
                            }
                        }

                    }
                }
            }


        }

    }
    private fun clearError(){
        _state.value =  _state.value.copy(error = null)
    }
}