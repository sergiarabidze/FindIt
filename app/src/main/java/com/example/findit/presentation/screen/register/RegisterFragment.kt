package com.example.findit.presentation.screen.register

import android.graphics.Color
import android.text.SpannableString
import android.text.method.LinkMovementMethod
import android.text.style.ForegroundColorSpan
import android.text.style.UnderlineSpan
import android.util.Log.d
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.findit.R
import com.example.findit.databinding.FragmentRegisterBinding
import com.example.findit.domain.resource.RegisterForm
import com.example.findit.presentation.base.BaseFragment
import com.example.findit.presentation.extension.launchCoroutine
import com.example.findit.presentation.extension.showSnackBar
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
        binding.etEmail.addTextChangedListener {
            addFieldsListener()
        }
        binding.etPassword.addTextChangedListener {
            addFieldsListener()
        }
        binding.etConfirmPassword.addTextChangedListener {
            addFieldsListener()
        }
        binding.etFirstName.addTextChangedListener {
            addFieldsListener()
        }
        binding.etLastName.addTextChangedListener {
            addFieldsListener()
        }
        binding.etPhone.addTextChangedListener {
            addFieldsListener()
        }

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
            viewModel.registerState.collectLatest { state ->
                d("TAG", "setObservers: $state")
                binding.progressBar.isVisible = state.isLoading
                binding.btnSignUp.isEnabled = !state.isLoading && state.btnEnabled
                binding.btnSignUp.isClickable = !state.isLoading && state.btnEnabled

                state.error?.let {
                    binding.root.showSnackBar(state.error)
                    viewModel.clearError()
                }

            }
        }
        launchCoroutine {
            viewModel.registerEvent.collectLatest { event ->
                when (event) {
                    RegisterUiEvent.NavigateToLoginScreen -> {
                        navigateToLoginScreen()
                    }
                    RegisterUiEvent.NavigateToHomeScreen -> {
                        navigateToHomeScreen()
                    }
                }
            }
        }

    }

    private fun handleRegistrationClick() {

        val registerForm = RegisterForm(
            firstName = binding.etFirstName.text.toString().trim(),
            lastName = binding.etLastName.text.toString().trim(),
            phone = binding.etPhone.text.toString().trim(),
            email = binding.etEmail.text.toString().trim(),
            password = binding.etPassword.text.toString().trim(),
            confirmPassword = binding.etConfirmPassword.text.toString().trim()
        )
        viewModel.onEvent(RegisterEvent.SubmitRegisterForm(registerForm))
    }
    private fun addFieldsListener() {
        val registerForm = RegisterForm(
            firstName = binding.etFirstName.text.toString().trim(),
            lastName = binding.etLastName.text.toString().trim(),
            phone = binding.etPhone.text.toString().trim(),
            email = binding.etEmail.text.toString().trim(),
            password = binding.etPassword.text.toString().trim(),
            confirmPassword = binding.etConfirmPassword.text.toString().trim()
        )
        viewModel.onEvent(RegisterEvent.ValidateRegisterForm(registerForm))
    }

    private fun navigateToLoginScreen() {
        findNavController().popBackStack()
    }
    private fun navigateToHomeScreen() {
        val action = RegisterFragmentDirections.actionRegisterFragmentToHomeFragment()
        findNavController().navigate(action)
    }



}