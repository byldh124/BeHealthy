package com.moondroid.healthy.common

import android.util.Log
import com.moondroid.healthy.common.Extensions.log

private const val TAG = "참다"

object Extensions {
    fun Any.log(msg: String) {
        Log.e(TAG, "[${this.javaClass.simpleName}] $msg")
    }

    fun Throwable.logException() {
        Log.e(TAG, "[Throwable] ${this.stackTraceToString()}")
    }
}