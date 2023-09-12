package com.moondroid.legacy.utils

import android.annotation.SuppressLint
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.google.android.material.card.MaterialCardView
import com.moondroid.legacy.R
import com.moondroid.behelthy.common.Extensions.logException

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

    @BindingAdapter("boxTextColor")
    @JvmStatic
    fun TextView.setBoxTextColor(index: Int ) {
        val textColor = when (index) {
            1, 2, 4, 5, 8, 12, 16, 17, 18 -> ContextCompat.getColor(context, R.color.defaultText)
            else -> ContextCompat.getColor(context, R.color.defaultText_white)
        }
        setTextColor(textColor)
    }
}
