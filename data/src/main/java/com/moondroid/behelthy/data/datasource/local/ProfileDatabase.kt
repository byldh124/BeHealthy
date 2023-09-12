package com.moondroid.behelthy.data.datasource.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.moondroid.behelthy.data.model.dao.ProfileDao
import com.moondroid.behelthy.data.model.entity.ProfileEntity

@Database(entities = [ProfileEntity::class], version = 1)
abstract class ProfileDatabase: RoomDatabase() {
    abstract fun profileDao(): ProfileDao
}