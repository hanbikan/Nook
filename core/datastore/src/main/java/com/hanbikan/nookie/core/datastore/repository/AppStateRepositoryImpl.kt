package com.hanbikan.nookie.core.datastore.repository

import com.hanbikan.nookie.core.datastore.NkDataStore
import com.hanbikan.nookie.core.domain.repository.AppStateRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AppStateRepositoryImpl @Inject constructor(
    private val nkDataStore: NkDataStore
) : AppStateRepository {
    override fun getActiveUserId(): Flow<Int?> {
        return nkDataStore.activeUserIdFlow
    }

    override suspend fun setActiveUserId(id: Int) {
        nkDataStore.setActiveUserId(id)
    }
}