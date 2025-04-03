package com.example.findit.presentation.screen.register

import android.graphics.Color
import android.text.SpannableString
import android.text.method.LinkMovementMethod
import android.text.style.ForegroundColorSpan
import android.text.style.UnderlineSpan
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.findit.databinding.FragmentRegisterBinding
import com.example.findit.presentation.base.BaseFragment
import com.example.findit.presentation.extension.launchCoroutine
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class RegisterFragment : BaseFragment<FragmentRegisterBinding>(FragmentRegisterBinding::inflate) {

    private val viewModel: RegisterViewModel by viewModels()

    override fun setUp() {
        setupLoginNavigationText()
    }

    private fun setupLoginNavigationText() {
        val loginText = binding.tvLoginNav
        val spannableString = SpannableString("Already have an account? Log in")

        val startIndex = spannableString.indexOf("Log in")
        val endIndex = startIndex + "Log in".length

        spannableString.setSpan(UnderlineSpan(), startIndex, endIndex, 0)
        spannableString.setSpan(ForegroundColorSpan(Color.parseColor("#0077CC")), startIndex, endIndex, 0)

        loginText.text = spannableString
        loginText.movementMethod = LinkMovementMethod.getInstance()
    }

    override fun setListeners() {
        binding.btnSignUp.setOnClickListener {
            handleRegistrationClick()
        }

        binding.tvLoginNav.setOnClickListener {
            viewModel.onEvent(RegisterEvent.NavigateToLoginScreen)
        }
    }

    override fun setObservers() {
        launchCoroutine {
            viewModel.registerEvent.collectLatest { event ->
                when (event) {
                    RegisterUiEvent.NavigateToLoginScreen -> navigateToLoginScreen()
                    // Add other events as needed
                }
            }
        }
    }

    private fun handleRegistrationClick() {
        val name = binding.etFirstName.text.toString().trim()
        val surname = binding.etLastName.text.toString().trim()
        val phone = binding.etPhone.text.toString().trim()
        val email = binding.etEmail.text.toString().trim()
        val password = binding.etPassword.text.toString().trim()
        val confirmPassword = binding.etConfirmPassword.text.toString().trim()

        viewModel.onEvent(
            RegisterEvent.SubmitRegistrationForm(
                firstName = name,
                lastName = surname,
                phoneNumber = phone,
                email = email,
                password = password,
                confirmPassword = confirmPassword
            )
        )
    }

    private fun navigateToLoginScreen() {
        findNavController().popBackStack()
    }
}