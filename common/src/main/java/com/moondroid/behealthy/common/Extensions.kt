package com.moondroid.behealthy.common

import android.util.Log

private const val TAG = "BeHealthy"

object Extensions {
    fun Any.debug(msg: String) {
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "[${this.javaClass.simpleName}] $msg")
        }
    }

    fun Throwable.logException() {
        Log.e(TAG, "[logException] ${this.stackTraceToString()}")
    }
}