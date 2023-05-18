package com.moondroid.behealthy.utils

import android.view.View
import androidx.databinding.BindingAdapter

object BindingAdapter {
    @BindingAdapter("visible")
    @JvmStatic
    fun View.visible(isVisible: Boolean) {
        if (isVisible) {
            this.visibility = View.VISIBLE
        } else {
            this.visibility = View.GONE
        }
    }
}
