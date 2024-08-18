package com.nook.core.domain_test.repository

import com.hanbikan.nook.core.domain.repository.AppStateRepository
import com.nook.core.domain_test.data.activeUserIdTestData
import com.nook.core.domain_test.data.lastVisitedRouteTestData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

val testAppStateRepository = TestAppStateRepository()

class TestAppStateRepository : AppStateRepository {
    override fun getActiveUserId(): Flow<Int?> {
        return flowOf(activeUserIdTestData)
    }

    override fun getLastVisitedRoute(): Flow<String?> {
        return flowOf(lastVisitedRouteTestData)
    }

    override fun getLanguage(): Flow<String?> {
        return flowOf("")
    }

    override fun getVersionName(): Flow<String?> {
        return flowOf("")
    }

    override suspend fun setActiveUserId(id: Int) {
        activeUserIdTestData = id
    }

    override suspend fun setLastVisitedRoute(route: String) {
        lastVisitedRouteTestData = route
    }

    override suspend fun setLanguage(language: String) {

    }

    override suspend fun setVersionName(versionName: String) {

    }
}