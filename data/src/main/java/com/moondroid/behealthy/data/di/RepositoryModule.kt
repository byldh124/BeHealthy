package com.moondroid.behealthy.data.di

import com.moondroid.behealthy.data.repository.AppRepositoryImpl
import com.moondroid.behealthy.domain.repository.AppRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun provideAppRepository(repository: AppRepositoryImpl) : AppRepository

}