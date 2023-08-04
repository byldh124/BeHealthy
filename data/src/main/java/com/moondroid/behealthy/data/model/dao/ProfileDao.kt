package com.moondroid.behealthy.data.model.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.moondroid.behealthy.common.RoomParam
import com.moondroid.behealthy.data.model.entity.ProfileEntity

@Dao
interface ProfileDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertProfile(vararg profileEntity: ProfileEntity)

    @Query("SELECT * FROM ${RoomParam.PROFILE_TABLE_NAME}")
    fun getProfile(): List<ProfileEntity>

    @Query("DELETE FROM ${RoomParam.PROFILE_TABLE_NAME}")
    fun deleteAll()

    @Delete
    fun delete(vararg: ProfileEntity)
}