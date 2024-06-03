package com.hanbikan.nook.core.datastore.repository

import com.hanbikan.nook.core.datastore.NkDataStore
import com.hanbikan.nook.core.domain.repository.AppStateRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AppStateRepositoryImpl @Inject constructor(
    private val nkDataStore: NkDataStore
) : AppStateRepository {
    override fun getActiveUserId(): Flow<Int?> {
        return nkDataStore.activeUserIdFlow
    }

    override fun getLastVisitedRoute(): Flow<String?> {
        return nkDataStore.lastVisitedRouteFlow
    }

    override fun getLanguage(): Flow<String?> {
        return nkDataStore.languageFlow
    }

    override fun getVersionName(): Flow<String?> {
        return nkDataStore.versionNameFlow
    }

    override suspend fun setActiveUserId(id: Int) {
        nkDataStore.setActiveUserId(id)
    }

    override suspend fun setLastVisitedRoute(route: String) {
        nkDataStore.setLastVisitedRoute(route)
    }

    override suspend fun setLanguage(language: String) {
        nkDataStore.setLanguage(language)
    }

    override suspend fun setVersionName(versionName: String) {
        nkDataStore.setVersionName(versionName)
    }
}