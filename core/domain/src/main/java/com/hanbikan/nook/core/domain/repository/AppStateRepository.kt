package com.hanbikan.nook.core.domain.repository

import kotlinx.coroutines.flow.Flow

interface AppStateRepository {
    fun getActiveUserId(): Flow<Int?>

    suspend fun setActiveUserId(id: Int)
}