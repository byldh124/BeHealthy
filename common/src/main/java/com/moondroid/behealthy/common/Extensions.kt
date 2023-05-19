package com.moondroid.behealthy.common

import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

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

    fun LifecycleOwner.repeatOnStarted(block: suspend CoroutineScope.() -> Unit) {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED, block)
        }
    }
}