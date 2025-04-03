package com.example.findit.presentation.screen.login

import android.graphics.Color
import android.text.SpannableString
import android.text.method.LinkMovementMethod
import android.text.style.ForegroundColorSpan
import android.text.style.UnderlineSpan
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.findit.databinding.FragmentLoginBinding
import com.example.findit.presentation.base.BaseFragment
import com.example.findit.presentation.extension.launchCoroutine
import com.example.findit.presentation.extension.showSnackBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {
    private val viewModel: LoginViewModel by viewModels()

    override fun setUp() {
        val registerText = binding.registerText
        val spannableString = SpannableString("You don't have an account? Register now")

        val startIndex = spannableString.indexOf("Register now")
        val endIndex = startIndex + "Register now".length

        spannableString.setSpan(UnderlineSpan(), startIndex, endIndex, 0)
        spannableString.setSpan(ForegroundColorSpan(Color.parseColor("#0077CC")), startIndex, endIndex, 0)

        registerText.text = spannableString

        registerText.movementMethod = LinkMovementMethod.getInstance()

    }

    override fun setListeners() {
        binding.registerText.setOnClickListener{
            viewModel.onEvent(LoginEvent.NavigateToRegisterScreen)
        }
        binding.btnLogin.setOnClickListener {
            handleLoginClick()
        }
    }

    override fun setObservers(){
        launchCoroutine {
            viewModel.loginEvent.collectLatest { event ->
                when (event){
                    LoginUiEvent.NavigateToHomeScreen ->{

                    }
                    LoginUiEvent.NavigateToRegisterScreen ->{
                        navigateToRegisterScreen()
                    }
                }
            }

            launchCoroutine {
                viewModel.loginState.collectLatest { state ->
                    binding.progressBar.isVisible = state.isLoading
                    binding.btnLogin.isEnabled = !state.isLoading
                    state.error?.let {
                        binding.root.showSnackBar(state.error)
                        viewModel.clearError()
                    }
                    }
                }
            }
        }






    private fun handleLoginClick(){
        val email = binding.etEmail.text.toString().trim()
        val password = binding.etPassword.text.toString().trim()
        viewModel.onEvent(LoginEvent.SubmitLoginForm(email = email, password = password))
    }


    private fun navigateToRegisterScreen(){
        val action = LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
        findNavController().navigate(action)
    }



}