package com.example.findit.presentation.screen.editprofile

import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import com.example.findit.databinding.FragmentEditProfileBinding
import com.example.findit.presentation.base.BaseFragment
import com.example.findit.presentation.extension.launchCoroutine
import com.example.findit.presentation.extension.showSnackBar
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class EditProfileFragment : BaseFragment<FragmentEditProfileBinding>(
    FragmentEditProfileBinding::inflate
) {

    private val viewModel: EditProfileViewModel by viewModels()

    override fun setUp() {
        viewModel.onEvent(EditProfileEvent.LoadProfile)
    }

    override fun setListeners() = with(binding) {
        etName.doAfterTextChanged {
            viewModel.onEvent(EditProfileEvent.OnNameChanged(it.toString()))
        }
        etSurname.doAfterTextChanged {
            viewModel.onEvent(EditProfileEvent.OnSurnameChanged(it.toString()))
        }
        etPhone.doAfterTextChanged {
            viewModel.onEvent(EditProfileEvent.OnPhoneChanged(it.toString()))
        }
        etEmail.doAfterTextChanged {
            viewModel.onEvent(EditProfileEvent.OnEmailChanged(it.toString()))
        }
        etPassword.doAfterTextChanged {
            viewModel.onEvent(EditProfileEvent.OnPasswordChanged(it.toString()))
        }

        btnSave.setOnClickListener {
            viewModel.onEvent(EditProfileEvent.OnSaveClicked)
        }
    }

    override fun setObservers() {
        launchCoroutine {
            viewModel.state.collect { state ->
                binding.apply {
                    if (etName.text.toString() != state.name) etName.setText(state.name)
                    if (etSurname.text.toString() != state.surname) etSurname.setText(state.surname)
                    if (etPhone.text.toString() != state.phone) etPhone.setText(state.phone)
                    if (etEmail.text.toString() != state.email) etEmail.setText(state.email)
                    if (etPassword.text.toString() != state.password) etPassword.setText(state.password)
                    btnSave.isEnabled = !state.isLoading
                }
            }
        }

        launchCoroutine {
            viewModel.effect.collect { effect ->
                when (effect) {
                    is EditProfileEffect.ProfileSaved -> {
                        binding.root.showSnackBar("Profile Saved")
                    }

                    is EditProfileEffect.ShowError -> {
                        binding.root.showSnackBar(effect.message)
                    }
                }
            }
        }
    }
}
