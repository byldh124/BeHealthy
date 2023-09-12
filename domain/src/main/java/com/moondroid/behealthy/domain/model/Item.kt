package com.moondroid.behealthy.domain.model

import android.annotation.SuppressLint
import com.moondroid.behealthy.common.ItemType
import com.moondroid.behealthy.common.TimeHelper
import java.text.SimpleDateFormat
import java.util.Date

data class Item(
    val index: Int,
    val id: String,
    val type: ItemType,
    val startDate: Long,
    val amount: Float,
    val cost: Long,
    val boxColor: Int,
) {

    private fun getTypeStr() = when (type) {
        ItemType.SMOKE -> "금연"
        ItemType.DRINK -> "금주"
        else -> ""
    }

    fun getBoxTitle() = "${getTypeStr()} 시작"

    @SuppressLint("SimpleDateFormat")
    fun getBoxStartDate(): String {
        val dateFormat = SimpleDateFormat("yyyy년 MM월 dd일").format(Date(startDate))
        return "${getTypeStr()} 시작일 : $dateFormat"
    }

    fun getBoxStayDate(): String {
        val now = System.currentTimeMillis()
        val stayTime = now - startDate
        val day = stayTime / TimeHelper.DAY
        val hour = (stayTime % TimeHelper.DAY) / TimeHelper.HOUR
        val minute = (((stayTime % TimeHelper.DAY)) % TimeHelper.HOUR) / TimeHelper.MINUTE

        return "${getTypeStr()} 유지일 : $day 일 $hour 시간 $minute 분"
    }
}