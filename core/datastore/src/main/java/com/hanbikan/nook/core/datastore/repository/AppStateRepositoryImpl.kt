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

    override fun getLanguage(): Flow<String?> {
        return nkDataStore.languageFlow
    }

    override fun getVersionName(): Flow<String?> {
        return nkDataStore.versionNameFlow
    }

    override fun getHasMuseumGuideShown(): Flow<Boolean> {
        return nkDataStore.hasMuseumGuideShown
    }

    override fun getTodoGraphRoute(): Flow<String?> {
        return nkDataStore.todoGraphRoute
    }

    override suspend fun setActiveUserId(id: Int) {
        nkDataStore.setActiveUserId(id)
    }

    override suspend fun setLanguage(language: String) {
        nkDataStore.setLanguage(language)
    }

    override suspend fun setVersionName(versionName: String) {
        nkDataStore.setVersionName(versionName)
    }

    override suspend fun setHasMuseumGuideShown(flag: Boolean) {
        nkDataStore.setHasMuseumGuideShown(flag)
    }

    override suspend fun setTodoGraphRoute(route: String) {
        nkDataStore.setTodoGraphRoute(route)
    }
}