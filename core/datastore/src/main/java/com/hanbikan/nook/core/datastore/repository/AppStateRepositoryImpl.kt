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

    override suspend fun setActiveUserId(id: Int) {
        nkDataStore.setActiveUserId(id)
    }

    override suspend fun setLastVisitedRoute(route: String) {
        nkDataStore.setLastVisitedRoute(route)
    }
}