package com.example.findit.presentation.screen.home

import android.util.Log.d
import androidx.core.view.isGone
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.findit.databinding.FragmentHomeBinding
import com.example.findit.presentation.base.BaseFragment
import com.example.findit.presentation.extension.launchCoroutine
import com.example.findit.presentation.extension.showSnackBar
import com.example.findit.presentation.screen.home.helper.PostAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate){
    private val viewModel: HomeScreenViewModel by viewModels()

    private val adapter : PostAdapter by lazy {
        PostAdapter{
            viewModel.onEvent(HomeScreenEvent.OnPostClicked(it))
        }
    }

    override fun setRecycler() {
        with(binding){
            postsRecyclerView.adapter = adapter
            searchEditText.doOnTextChanged { text, _, _, _ ->
                viewModel.onEvent(HomeScreenEvent.OnSearchQueryChanged(text.toString()))
            }
        }
    }

    override fun setObservers() {
        launchCoroutine {
            viewModel.state.collectLatest { state->
                with(binding) {
                    if (state.posts.isNotEmpty()) {
                        adapter.submitList(state.posts)
                        postsRecyclerView.isGone = false
                        noPostsFoundText.isGone = true
                    } else {
                        postsRecyclerView.isGone = true
                        noPostsFoundText.isGone = false
                    }
                    progressBar.isGone = !state.isLoading
                    state.error?.let {
                        root.showSnackBar(it)
                        viewModel.onEvent(HomeScreenEvent.ClearError)
                    }

                }

            }

        }
        launchCoroutine {
            viewModel.effect.collectLatest {
                when(it){
                    is HomeScreenEffect.NavigateToPostInfo ->{
                        openPost(it.postId)
                    }
                }
            }
        }
    }

    private fun openPost(postId : String){
        val action = HomeFragmentDirections.actionHomeFragmentToPostFragment(postId)
        findNavController().navigate(action)
    }

}