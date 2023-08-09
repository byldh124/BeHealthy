package com.moondroid.behealthy.utils

import android.annotation.SuppressLint
import android.view.View
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.google.android.material.card.MaterialCardView
import com.moondroid.behealthy.common.Extensions.logException

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

    @SuppressLint("DiscouragedApi")
    @BindingAdapter("boxColor")
    @JvmStatic
    fun MaterialCardView.setBoxColor(index: Int) {
        try {
            val resId =
                context.resources.getIdentifier(String.format("box%02d", index), "color", context.packageName)
            setCardBackgroundColor(ContextCompat.getColor(context, resId))
        } catch (e: Exception) {
            e.logException()
        }
    }
}
