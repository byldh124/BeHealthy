package com.moondroid.behealthy.data.datasource.local

import com.moondroid.behealthy.data.model.entity.ProfileEntity

interface LocalDataSource {
    suspend fun deleteAllProfile()
    suspend fun insertProfile(profile: ProfileEntity)
    suspend fun getProfile(): List<ProfileEntity>
    suspend fun deleteProfile(profile: ProfileEntity)
    suspend fun isTutorial() : Boolean
}