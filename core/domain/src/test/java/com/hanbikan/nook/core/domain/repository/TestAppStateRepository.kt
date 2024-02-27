package com.hanbikan.nook.core.domain.repository

import com.hanbikan.nook.core.domain.data.activeUserIdTestData
import com.hanbikan.nook.core.domain.data.lastVisitedRouteTestData
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

    override suspend fun setActiveUserId(id: Int) {
        activeUserIdTestData = id
    }

    override suspend fun setLastVisitedRoute(route: String) {
        lastVisitedRouteTestData = route
    }
}