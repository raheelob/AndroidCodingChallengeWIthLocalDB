package com.example.maydcodingchallenge

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import com.example.maydcodingchallenge.databinding.ActivityMainBinding
import com.example.maydcodingchallenge.utils.LoadingViewDialog
import com.example.maydcodingchallenge.view.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, true)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    var mDialog = LoadingViewDialog(this)
    fun showLoading() = mDialog.showDialog()
    fun hideLoading() = mDialog.hideDialog()
}