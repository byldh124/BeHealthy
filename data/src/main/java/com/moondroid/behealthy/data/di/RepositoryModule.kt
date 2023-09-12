package com.moondroid.behealthy.data.di

import com.moondroid.behealthy.data.repository.AppRepositoryImpl
import com.moondroid.behealthy.data.repository.ItemRepositoryImpl
import com.moondroid.behealthy.domain.repository.AppRepository
import com.moondroid.behealthy.domain.repository.ItemRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun provideAppRepository(repository: AppRepositoryImpl): AppRepository

    @Binds
    fun provideItemRepository(repository: ItemRepositoryImpl): ItemRepository
}