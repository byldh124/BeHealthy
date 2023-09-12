package com.moondroid.behealthy.common

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date

private const val TAG = "BeHealthy"

object Extensions {
    fun Any.debug(msg: String) {
        if (BuildConfig.DEBUG) {
            Log.e(TAG, "[${this.javaClass.simpleName}] $msg")
        }
    }

    fun Activity.exitApp() {
        this.moveTaskToBack(true)
        this.finish()
        android.os.Process.killProcess(android.os.Process.myPid())
    }

    fun Throwable.logException() {
        Log.e(TAG, "[logException] ${this.stackTraceToString()}")
    }

    fun LifecycleOwner.repeatOnStarted(block: suspend CoroutineScope.() -> Unit) {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED, block)
        }
    }

    fun Context.toast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
        this.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(editable: Editable?) {
                afterTextChanged.invoke(editable.toString())
            }
        })
    }

    @SuppressLint("SimpleDateFormat")
    fun getToday(pattern: String = "yyMMddHhmmss") =
        SimpleDateFormat(pattern).format(Date(System.currentTimeMillis()))
}