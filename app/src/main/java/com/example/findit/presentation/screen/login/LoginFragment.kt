package com.example.findit.presentation.screen.login

import android.graphics.Color
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.text.style.UnderlineSpan
import android.util.Log.d
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.example.findit.R
import com.example.findit.databinding.FragmentLoginBinding
import com.example.findit.presentation.base.BaseFragment
import com.example.findit.presentation.extension.hideKeyboard
import com.example.findit.presentation.extension.launchCoroutine
import com.example.findit.presentation.extension.showSnackBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {
    private val viewModel: LoginViewModel by viewModels()

    override fun setUp() {
        setupSignUpNavigationText()
    }

    override fun setListeners() {
        binding.btnLogin.setOnClickListener {
            it.hideKeyboard()
            handleLoginClick()
        }

    }

    override fun setObservers() {
        launchCoroutine {
            viewModel.loginEvent.collectLatest { event ->
                when (event) {
                    LoginUiEvent.NavigateToHomeScreen -> {
                        navigateToHomeScreen()
                    }

                    LoginUiEvent.NavigateToRegisterScreen -> {
                        navigateToRegisterScreen()
                    }
                }
            }
        }

            launchCoroutine {
                viewModel.loginState.collectLatest { state ->
                    binding.progressBar.isVisible = state.isLoading
                    binding.btnLogin.isEnabled = !state.isLoading
                    if (!state.btnEnabled) {
                        binding.btnLogin.setBackgroundColor(R.color.background_color)
                    } else {
                        binding.btnLogin.setBackgroundColor(R.color.ic_launcher_background)
                    }
                    d("LoginFragment", "Login state: ${state.error}")
                    state.error?.let {
                        d("LoginFragment", "Error: $it")
                        binding.root.showSnackBar(state.error)
                        viewModel.onEvent(LoginEvent.ClearError)
                    }
                }
            }
    }


    private fun handleLoginClick() {
        val email = binding.etEmail.text.toString().trim()
        val password = binding.etPassword.text.toString().trim()
        viewModel.onEvent(LoginEvent.SubmitLoginForm(email = email, password = password))
    }


    private fun navigateToRegisterScreen() {
        val action = LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
        findNavController().navigate(action)
    }
    private fun navigateToHomeScreen(){
        val action = LoginFragmentDirections.actionLoginFragmentToHomeFragment()
        findNavController().navigate(action)
    }

    private fun setupSignUpNavigationText() {
        val registerText = binding.registerText

        val fullText = getString(R.string.you_don_t_have_an_account_register_now)
        val targetText = getString(R.string.register_now)
        val spannableString = SpannableString(fullText)

        val startIndex = fullText.indexOf(targetText)
        val endIndex = startIndex + targetText.length

        if (startIndex == -1) return

        val clickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                viewModel.onEvent(LoginEvent.NavigateToRegisterScreen)
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.isUnderlineText = true
                ds.color = Color.parseColor("#0077CC")
            }
        }

        spannableString.setSpan(clickableSpan, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        registerText.text = spannableString
        registerText.movementMethod = LinkMovementMethod.getInstance()
        registerText.highlightColor = Color.TRANSPARENT
    }



}