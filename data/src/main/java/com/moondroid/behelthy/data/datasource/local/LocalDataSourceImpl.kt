package com.moondroid.behelthy.data.datasource.local

import com.moondroid.behelthy.data.model.dao.ProfileDao
import com.moondroid.behelthy.data.model.entity.ProfileEntity
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(
    private val profileDao: ProfileDao,
    private val dataStoreManager: DataStoreManager,
) : LocalDataSource {
    override suspend fun deleteAllProfile() = profileDao.deleteAll()
    override suspend fun insertProfile(profile: ProfileEntity) = profileDao.insertProfile(profile)
    override suspend fun getProfile() = profileDao.getProfile()
    override suspend fun deleteProfile(profile: ProfileEntity) = profileDao.delete(profile)
    override suspend fun isTutorial(): Boolean = dataStoreManager.isTutorial()
}