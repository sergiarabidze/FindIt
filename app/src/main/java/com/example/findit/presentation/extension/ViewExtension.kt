package com.example.findit.presentation.extension

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar

fun ImageView.loadImage(url: String,placeHolder: Int) {
    Glide.with(this)
        .load(url)
        .error(placeHolder)
        .placeholder(placeHolder)
        .into(this)
}

fun View.hideKeyboard(){
    val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(windowToken,0)
}
fun View.showSnackBar(message: String) {
    this.let {
        Snackbar.make(it, message, Snackbar.LENGTH_SHORT).show()
    }
}