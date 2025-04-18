package com.example.findit.presentation.screen.addpost

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.findit.data.mapper.toDomain
import com.example.findit.domain.model.PostDomain
import com.example.findit.domain.resource.Resource
import com.example.findit.domain.usecase.GetCurrentUserIdUseCase
import com.example.findit.domain.usecase.UploadPostPhotoUseCase
import com.example.findit.domain.usecase.UploadPostUseCase
import com.example.findit.domain.model.PostType
import com.google.firebase.firestore.GeoPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class AddPostViewModel @Inject constructor(
    @ApplicationContext private val  context: Context,
    private val uploadPostPhotoUseCase: UploadPostPhotoUseCase,
    private val getCurrentUserIdUseCase: GetCurrentUserIdUseCase,
    private val uploadPostUseCase: UploadPostUseCase
) : ViewModel(){

    private val _state = MutableStateFlow(AddPostState())
    val state = _state.asStateFlow()

    private val _event = MutableSharedFlow<AddPostUiEvent>()
    val event = _event.asSharedFlow()

    fun onEvent(event: AddPostEvent) {
        when(event) {
            is AddPostEvent.AddLocation -> {
            }
            is AddPostEvent.OpenDialog ->{
                handleOpenDialog()
            }
            is AddPostEvent.AddPost ->{
                addPost(event.type,event.description,event.geoPoint)
            }
            is AddPostEvent.ClearError -> {
                clearError()
            }

            is AddPostEvent.ImageSelected ->{
                compressImage(context ,event.uri)
            }
        }
    }

    private fun compressImage(context: Context, uri: Uri) {

        viewModelScope.launch(Dispatchers.IO) {

            try {

                context.contentResolver.openInputStream(uri)?.use { inputStream ->
                    val options = BitmapFactory.Options().apply {
                        inSampleSize = 2
                        inJustDecodeBounds = false
                    }

                    val originalBitmap = BitmapFactory.decodeStream(inputStream, null, options)
                        ?: throw IOException("Failed to decode bitmap")

                    val outputStream = ByteArrayOutputStream().apply {
                        if (!originalBitmap.compress(Bitmap.CompressFormat.JPEG, 80, this)) {
                            throw IOException("Failed to compress bitmap")
                        }
                    }

                    val compressedBitmap = BitmapFactory.decodeByteArray(
                        outputStream.toByteArray(),
                        0,
                        outputStream.size()
                    )
                    _state.value = _state.value.copy(bitmap = compressedBitmap)
                }
            } catch (e: Exception) {
                _state.value = _state.value.copy(btnEnabled = false, error = e.localizedMessage)
            }
        }
    }

    private fun addPost(type : PostType, description: String, geoPoint: GeoPoint){
        viewModelScope.launch {
            state.value.bitmap?.let { uploadPostPhotoUseCase(it) }?.collectLatest { resource ->
                when(resource){
                    is Resource.Error -> {
                        _state.value = _state.value.copy(error = resource.errorMessage)
                    }
                    is Resource.Loader ->{
                        _state.value = _state.value.copy(isLoading = resource.isLoading)
                    }
                    is Resource.Success ->{
                        val currentUser = getCurrentUserIdUseCase()
                        val post = PostDomain(
                            imageUrl = resource.data,
                            description = description,
                            userId = currentUser ?: "",
                            location = geoPoint.toDomain(),
                            timestamp = System.currentTimeMillis(),
                            postType = type
                        )
                        uploadPostUseCase(post).collectLatest{
                            when(it){
                                is Resource.Error ->{
                                    _state.value = _state.value.copy(error = it.errorMessage)
                                }
                                is Resource.Loader ->{
                                    _state.value = _state.value.copy(isLoading = it.isLoading)
                                }
                                is Resource.Success ->{
                                    _event.emit(AddPostUiEvent.AddPost)
                                }
                            }
                        }
                    }
                }

            }

        }
    }


    private fun handleOpenDialog() {
        viewModelScope.launch {
            _event.emit(AddPostUiEvent.OpenImageOptions)
        }
    }

    private fun clearError(){
        _state.value = _state.value.copy(error = null)
    }

}