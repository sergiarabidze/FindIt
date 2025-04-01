package com.example.findit.presentation.extension

import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.launch

fun Fragment.showToast(message: String) {
    val toast = android.widget.Toast.makeText(requireContext(), message, android.widget.Toast.LENGTH_SHORT)
    toast.show()
}

fun Fragment.launchCoroutine(block: suspend () -> Unit) {
    viewLifecycleOwner.lifecycleScope.launch {
        repeatOnLifecycle(androidx.lifecycle.Lifecycle.State.STARTED) {
            block.invoke()
        }
    }
}