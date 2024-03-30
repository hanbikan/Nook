package com.hanbikan.nook.core.network.di

import com.hanbikan.nook.core.domain.repository.RemoteCollectionRepository
import com.hanbikan.nook.core.network.repository.RemoteCollectionRepositoryImpl
import com.hanbikan.nook.core.network.service.NookipediaService
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
interface RepositoryModule {

    @Binds
    @Singleton
    fun provideCollectionRepository(collectionRepositoryImpl: RemoteCollectionRepositoryImpl): RemoteCollectionRepository

    companion object {
        @Provides
        @Singleton
        fun provideNookipediaService(retrofit: Retrofit): NookipediaService {
            return retrofit.create(NookipediaService::class.java)
        }
    }
}