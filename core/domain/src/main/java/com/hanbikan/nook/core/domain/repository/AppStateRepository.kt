package com.hanbikan.nook.core.domain.repository

import kotlinx.coroutines.flow.Flow

interface AppStateRepository {
    fun getActiveUserId(): Flow<Int?>

    fun getLanguage(): Flow<String?>

    fun getVersionName(): Flow<String?>

    fun getHasMuseumGuideShown(): Flow<Boolean>

    fun getTodoGraphRoute(): Flow<String?>

    suspend fun setActiveUserId(id: Int)

    suspend fun setLanguage(language: String)

    suspend fun setVersionName(versionName: String)

    suspend fun setHasMuseumGuideShown(flag: Boolean)

    suspend fun setTodoGraphRoute(route: String)
}