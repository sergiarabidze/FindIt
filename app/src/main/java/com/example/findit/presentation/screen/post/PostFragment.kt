package com.example.findit.presentation.screen.post

import android.content.Intent
import android.net.Uri
import android.util.Log.d
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.findit.R
import com.example.findit.databinding.FragmentPostBinding
import com.example.findit.presentation.base.BaseFragment
import com.example.findit.presentation.extension.launchCoroutine
import com.example.findit.presentation.extension.loadImage
import com.example.findit.presentation.extension.showSnackBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class PostFragment : BaseFragment<FragmentPostBinding>(FragmentPostBinding::inflate) {
    private val viewModel: PostViewModel by viewModels()
    private val args: PostFragmentArgs by navArgs()

    override fun setData() {
        val postId = args.postId
        viewModel.onEvent(PostEvent.LoadPost(postId))
    }


    override fun setListeners() {
        with(binding){
            tvPhoneNumber.setOnClickListener {
                val phoneNumber = tvPhoneNumber.text.toString()
                val intent = Intent(Intent.ACTION_DIAL).apply {
                    data = Uri.parse(getString(R.string.tel, phoneNumber))
                }
                startActivity(intent)
            }
        }
    }

    override fun setObservers() {
        launchCoroutine {
            viewModel.state.collectLatest { state ->

                state.post?.let {
                    with(binding){
                        ivPostImage.loadImage(it.imageUrl,R.drawable.postdefault)
                        tvPostTimestamp.text = it.timestamp
                        tvPostDescription.text = it.description
                    }
                }

                state.user?.let {
                    with(binding){
                        tvPhoneNumber.text = it.phone
                        tvUserFullname.text = getString(R.string.fullname, it.name, it.surname)
                        tvUserEmail.text = it.email
                    }
                }

                state.error?.let {
                    binding.root.showSnackBar(it)
                }
            }
        }
    }
}