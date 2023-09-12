package com.moondroid.behelthy.data.di

import com.moondroid.behelthy.data.repository.AppRepositoryImpl
import com.moondroid.behelthy.data.repository.ItemRepositoryImpl
import com.moondroid.behelthy.domain.repository.AppRepository
import com.moondroid.behelthy.domain.repository.ItemRepository
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