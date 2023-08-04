package com.moondroid.behealthy.data.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.moondroid.behealthy.common.RoomParam
import com.moondroid.behealthy.data.datasource.local.LocalDataSource
import com.moondroid.behealthy.data.model.dao.ProfileDao
import com.moondroid.behealthy.data.datasource.local.ProfileDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideContext(application: Application): Context = application

    @Singleton
    @Provides
    fun provideProfileDatabases (
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(
        app,
        ProfileDatabase::class.java,
        RoomParam.PROFILE_TABLE_NAME
    ).build()


    @Singleton
    @Provides
    fun provideProfileDao(db: ProfileDatabase) = db.profileDao() // The reason we can implement a Dao for the database
}