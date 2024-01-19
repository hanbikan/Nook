package com.hanbikan.nooknook.core.datastore.repository

import com.hanbikan.nooknook.core.datastore.NnDataStore
import com.hanbikan.nooknook.core.domain.repository.AppStateRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AppStateRepositoryImpl @Inject constructor(
    private val nnDataStore: NnDataStore
) : AppStateRepository {
    override fun getActiveUserId(): Flow<Int?> {
        return nnDataStore.activeUserIdFlow
    }

    override suspend fun setActiveUserId(id: Int) {
        nnDataStore.setActiveUserId(id)
    }
}