package com.moondroid.behelthy.utils.firebase

import com.google.firebase.crashlytics.FirebaseCrashlytics

object FBCrash {
    private val crashlytics = FirebaseCrashlytics.getInstance()

    fun setProperty(userId: String) {
        crashlytics.setUserId(userId)
    }

    fun logException(t: Throwable) {
        try {
            val message = t.stackTraceToString()
            crashlytics.log("[logException]::$message")
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}