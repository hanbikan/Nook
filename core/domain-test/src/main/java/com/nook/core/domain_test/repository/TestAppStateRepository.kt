package com.nook.core.domain_test.repository

import com.hanbikan.nook.core.domain.repository.AppStateRepository
import com.nook.core.domain_test.data.activeUserIdTestData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

val testAppStateRepository = TestAppStateRepository()

class TestAppStateRepository : AppStateRepository {
    override fun getActiveUserId(): Flow<Int?> {
        return flowOf(activeUserIdTestData)
    }

    override fun getLanguage(): Flow<String?> {
        return flowOf("")
    }

    override fun getVersionName(): Flow<String?> {
        return flowOf("")
    }

    override fun getHasMuseumGuideShown(): Flow<Boolean> {
        return flowOf(false)
    }

    override suspend fun setActiveUserId(id: Int) {
        activeUserIdTestData = id
    }

    override suspend fun setLanguage(language: String) {

    }

    override suspend fun setVersionName(versionName: String) {

    }

    override suspend fun setHasMuseumGuideShown(flag: Boolean) {

    }
}