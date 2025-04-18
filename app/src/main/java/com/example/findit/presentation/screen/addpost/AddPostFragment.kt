package com.example.findit.presentation.screen.addpost

import android.net.Uri
import android.os.Environment
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.core.view.isGone
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.findit.R
import com.example.findit.databinding.FragmentAddPostBinding
import com.example.findit.presentation.base.BaseFragment
import com.example.findit.presentation.extension.launchCoroutine
import com.example.findit.presentation.extension.showSnackBar
import com.example.findit.presentation.screen.add_image_dialog.ImageOptionDialog
import com.example.findit.presentation.screen.model.ImagePickOption
import com.google.firebase.firestore.GeoPoint
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import java.io.File

@AndroidEntryPoint
class AddPostFragment : BaseFragment<FragmentAddPostBinding>(FragmentAddPostBinding::inflate) {

    private val viewModel : AddPostViewModel by viewModels()

    private var tempCameraUri: Uri? = null

    private val galleryLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            uri?.let {
                viewModel.onEvent(AddPostEvent.ImageSelected(it))
            }
        }

    private val cameraLauncher =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { success ->
            if (success) {
                tempCameraUri?.let { uri ->
                    viewModel.onEvent(AddPostEvent.ImageSelected(uri))
                    tempCameraUri = null
                }
            }
        }

    override fun setUp() {
        parentFragmentManager.setFragmentResultListener(
            ImageOptionDialog.REQUEST_KEY,
            viewLifecycleOwner
        ) { _, result ->
            val selected = result.getString(ImageOptionDialog.RESULT_KEY)
            val option = selected?.let { ImagePickOption.valueOf(it) }

            when (option) {
                ImagePickOption.CAMERA -> {
                    openCamera()
                }

                ImagePickOption.GALLERY -> {
                    openGallery()
                }
                null -> {}
            }
        }
    }

    override fun setListeners() {
        with(binding) {
            addPhotoId.setOnClickListener {
                viewModel.onEvent(AddPostEvent.OpenDialog)
            }

            addLocationId.setOnClickListener {
                viewModel.onEvent(AddPostEvent.AddLocation(GeoPoint(0.0,0.0)))
            }

            addPostId.setOnClickListener {
                addPost()
            }

        }
    }

    override fun setObservers() {
        launchCoroutine {
            viewModel.state.collectLatest{ state ->
                with(binding) {
                    photoId.setImageBitmap(state.bitmap)
                    photoId.isGone = state.bitmap == null
                    state.error?.let {
                        root.showSnackBar("raghacas urev")
                    }
                }
            }
        }
        launchCoroutine {
            viewModel.event.collectLatest{event->
                when(event){
                    AddPostUiEvent.OpenImageOptions ->{
                        openOptionsDialog()
                    }
                    AddPostUiEvent.OpenLocation ->{

                    }

                    AddPostUiEvent.AddPost ->{
                        binding.root.showSnackBar("successfully aitvirta")
                        navigateToHomeScreen()
                    }
                }
            }
        }
    }

    private fun openGallery() {
        galleryLauncher.launch(getString(R.string.image))
    }

    private fun openCamera() {
        getTempFileUri().let { uri ->
            tempCameraUri = uri
            cameraLauncher.launch(uri)
        }
    }

    private fun getTempFileUri(): Uri {
        val storageDir = requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val file = File.createTempFile(getString(R.string.img, System.currentTimeMillis().toString()),
            getString(
                R.string.jpg
            ), storageDir)
        return FileProvider.getUriForFile(
            requireContext(),
            getString(R.string.provider, requireContext().packageName),
            file
        )
    }

    private fun addPost(){
        with(binding){
            val desc = descriptionId.text.toString()
            viewModel.onEvent(AddPostEvent.AddPost(desc, geoPoint = GeoPoint(0.0,0.0)))
        }
    }

    private fun openOptionsDialog(){
        val action = AddPostFragmentDirections.actionAddPostFragmentToImageOptionDialog()
        findNavController().navigate(action)
    }

    private fun navigateToHomeScreen(){
        val action = AddPostFragmentDirections.actionAddPostFragmentToHomeFragment()
        findNavController().navigate(action)
    }

}