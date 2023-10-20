package com.moondroid.behealthy

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.kakao.sdk.common.KakaoSdk
import com.moondroid.behealthy.domain.model.Profile
import com.moondroid.behealthy.utils.NetworkConnection
import com.moondroid.behealthy.utils.firebase.FBAnalyze
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApp : Application() {
    companion object {
        lateinit var profile: Profile
    }

    override fun onCreate() {
        super.onCreate()
        //다크모드 지원 X
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        // Initialize NetworkConnection
        NetworkConnection.init(applicationContext)

        //Initialize Firebase Analytics
        FBAnalyze.init(applicationContext)

        /* initialize kakao */
        KakaoSdk.init(applicationContext, resources.getString(R.string.kakao_app_key))
    }
}