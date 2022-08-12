package com.example.maydcodingchallenge.view.adapter

import android.content.res.ColorStateList
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.example.maydcodingchallenge.R

object BindingAdapters {
    @BindingAdapter("isCopied")
    @JvmStatic
    fun isCopied(btn: AppCompatButton, isCopied: Boolean?) {
        if (isCopied == true) {
            btn.backgroundTintList =
                ColorStateList.valueOf(ContextCompat.getColor(btn.context, R.color.dark_violet))
            btn.text = "Copied"
        } else {
            btn.backgroundTintList =
                ColorStateList.valueOf(ContextCompat.getColor(btn.context, R.color.cyan))
            btn.text = "Copy"
        }
    }


}