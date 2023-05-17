package com.moondroid.healthy

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.moondroid.healthy.utils.NetworkConnection
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BeHealthyApp: Application() {
    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        NetworkConnection.init(applicationContext)
    }
}