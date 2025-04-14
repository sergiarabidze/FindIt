package com.example.findit.presentation.screen.profile

import android.content.res.Configuration
import androidx.core.app.ActivityCompat.recreate
import androidx.core.content.ContentProviderCompat
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.viewModels
import com.example.findit.databinding.FragmentProfileBinding
import com.example.findit.presentation.base.BaseFragment
import com.example.findit.presentation.extension.launchCoroutine
import java.util.Locale

class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {
    private val viewModel: ProfileViewModel by viewModels()
    override fun setUp() {

    }

    override fun setListeners() {
        binding.itemChangeLanguage.setOnClickListener {
            viewModel.onEvent(ProfileEvent.ChangeLanguageClicked)
        }
    }

    override fun setObservers() {
        launchCoroutine {
            viewModel.effect.collect { effect ->
                when (effect) {
                    is ProfileEffect.ChangeLanguage -> toggleLanguage()
                    ProfileEffect.NavigateToChangeTheme -> TODO()
                    ProfileEffect.NavigateToEditProfile -> TODO()
                    ProfileEffect.NavigateToLogin -> TODO()
                    ProfileEffect.NavigateToMyProfile -> TODO()
                }
            }
        }
    }




    private fun setLocale(languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val config = Configuration()
        config.setLocale(locale)
        requireContext().resources.updateConfiguration(config, requireContext().resources.displayMetrics)

        requireActivity().recreate()
    }


    private fun toggleLanguage() {
        val newLang = if (Locale.getDefault().language == "en") "ka" else "en"
        setLocale(newLang)
    }


}