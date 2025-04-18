package com.example.findit.presentation.base

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

typealias BottomSheetInflate<T> = (LayoutInflater, ViewGroup?, Boolean) -> T

abstract class BaseBottomSheetFragment<VB : ViewBinding>(
    private val inflate: BottomSheetInflate<VB>
) : BottomSheetDialogFragment() {

    private var _binding: VB? = null
    val binding get() = _binding!!

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return BottomSheetDialog(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = inflate.invoke(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUp()
        setListeners()
        setRecycler()
    }

    open fun setUp() = Unit

    open fun setListeners() = Unit

    open fun setRecycler() = Unit

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
