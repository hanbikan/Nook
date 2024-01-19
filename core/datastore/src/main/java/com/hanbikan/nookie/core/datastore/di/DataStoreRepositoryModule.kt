package com.hanbikan.nookie.core.datastore.di

import com.hanbikan.nookie.core.datastore.repository.AppStateRepositoryImpl
import com.hanbikan.nookie.core.domain.repository.AppStateRepository
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