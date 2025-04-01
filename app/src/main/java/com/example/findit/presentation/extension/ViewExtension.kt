package com.example.findit.presentation.extension

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import com.bumptech.glide.Glide

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