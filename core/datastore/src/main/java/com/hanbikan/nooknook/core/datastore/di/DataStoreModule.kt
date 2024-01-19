package com.hanbikan.nooknook.core.datastore.di

import android.content.Context
import com.hanbikan.nooknook.core.datastore.NnDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Provides
    @Singleton
    fun providesNnDataStore(
        @ApplicationContext context: Context
    ) : NnDataStore = NnDataStore(context)
}