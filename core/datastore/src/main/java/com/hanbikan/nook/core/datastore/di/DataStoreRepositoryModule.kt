package com.hanbikan.nook.core.datastore.di

import com.hanbikan.nook.core.datastore.repository.AppStateRepositoryImpl
import com.hanbikan.nook.core.domain.repository.AppStateRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataStoreRepositoryModule {

    @Binds
    fun bindsAppStateRepository(
        appStateRepositoryImpl: AppStateRepositoryImpl,
    ): AppStateRepository
}