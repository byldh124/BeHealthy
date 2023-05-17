package com.moondroid.healthy.view.ui

import androidx.appcompat.app.AppCompatActivity
import com.moondroid.healthy.databinding.ActivityMainBinding
import com.moondroid.healthy.utils.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val binding by viewBinding(ActivityMainBinding::inflate)
}