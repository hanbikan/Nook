package com.hanbikan.nook.core.domain.repository

import kotlinx.coroutines.flow.Flow

interface AppStateRepository {
    fun getActiveUserId(): Flow<Int?>

    fun getLastVisitedRoute(): Flow<String?>

    fun getLanguage(): Flow<String?>

    fun getVersionName(): Flow<String?>

    suspend fun setActiveUserId(id: Int)

    suspend fun setLastVisitedRoute(route: String)

    suspend fun setLanguage(language: String)

    suspend fun setVersionName(versionName: String)
}