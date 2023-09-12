package com.moondroid.legacy.view.base

import android.app.Dialog
import android.content.Context
import com.moondroid.legacy.R

open class BaseDialog(context: Context) : Dialog(context, R.style.DialogTheme) {
    fun exit() {
        if (isShowing) super.cancel()
    }
}