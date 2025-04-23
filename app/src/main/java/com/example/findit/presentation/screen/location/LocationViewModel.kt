package com.example.findit.presentation.screen.location

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.findit.domain.resource.Resource
import com.example.findit.domain.usecase.GetPostsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocationViewModel @Inject constructor(
    private val getUserPostsUseCase: GetPostsUseCase
):ViewModel(){
    private val _state = MutableStateFlow(LocationState())
    val state = _state.asStateFlow()

    private val _event = MutableSharedFlow<LocationEvent>()
    val event = _event.asSharedFlow()

    init {
        getUserPosts()
    }

    fun onEvent(){
    }


    private fun  getUserPosts(){
        viewModelScope.launch {
            getUserPostsUseCase().collectLatest{ state ->
                when(state){
                    is Resource.Error ->{
                        _state.value = _state.value.copy(error = state.errorMessage)
                    }

                    is Resource.Loader -> {
                        _state.value = _state.value.copy(isLoading = state.isLoading)
                    }

                    is Resource.Success ->{
                        _state.value = _state.value.copy(posts = state.data)
                    }
                }
            }

        }
    }
}