package com.moondroid.healthy.data.di

import com.moondroid.healthy.data.repository.AppRepositoryImpl
import com.moondroid.healthy.domain.repository.AppRepository
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