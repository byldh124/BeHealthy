package com.moondroid.legacy.data.di

import com.moondroid.legacy.data.repository.AppRepositoryImpl
import com.moondroid.legacy.data.repository.ItemRepositoryImpl
import com.moondroid.legacy.domain.repository.AppRepository
import com.moondroid.legacy.domain.repository.ItemRepository
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