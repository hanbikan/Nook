package com.hanbikan.nookie.core.datastore.di

import android.content.Context
import com.hanbikan.nookie.core.datastore.NkDataStore
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
    fun providesNkDataStore(
        @ApplicationContext context: Context
    ) : NkDataStore = NkDataStore(context)
}