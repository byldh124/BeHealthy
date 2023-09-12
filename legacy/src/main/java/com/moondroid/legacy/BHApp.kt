package com.moondroid.legacy

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.kakao.sdk.common.KakaoSdk
import com.moondroid.legacy.domain.model.Profile
import com.moondroid.legacy.utils.NetworkConnection
import com.moondroid.legacy.utils.firebase.FBAnalyze
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BHApp : Application() {

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