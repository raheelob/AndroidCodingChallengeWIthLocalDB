package com.example.maydcodingchallenge.utils

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.core.content.ContextCompat

fun Context.copyToClipboard(text: CharSequence) {
    val clip = ClipData.newPlainText("label", text)
    ContextCompat.getSystemService(this, ClipboardManager::class.java)?.setPrimaryClip(clip)
}

fun showToast(context: Context, messageToShow: String) {
    Toast.makeText(context, messageToShow, Toast.LENGTH_SHORT).show()
}