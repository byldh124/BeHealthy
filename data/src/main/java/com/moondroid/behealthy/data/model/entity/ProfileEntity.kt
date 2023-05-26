package com.moondroid.behealthy.data.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.moondroid.behealthy.common.RoomParam

@Entity(tableName = RoomParam.PROFILE_TABLE_NAME)
data class ProfileEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = RoomParam.PROFILE_ID)
    val id: String,
    @ColumnInfo(name = RoomParam.PROFILE_NAME)
    val name: String,
    @ColumnInfo(name = RoomParam.PROFILE_THUMB)
    val thumb: String,
    @ColumnInfo(name = RoomParam.PROFILE_TYPE)
    val type: Int
)