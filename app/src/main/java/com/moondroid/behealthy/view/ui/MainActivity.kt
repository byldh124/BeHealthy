package com.moondroid.behealthy.view.ui

import androidx.appcompat.app.AppCompatActivity
import com.moondroid.behealthy.databinding.ActivityMainBinding
import com.moondroid.behealthy.utils.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val binding by viewBinding(ActivityMainBinding::inflate)
}