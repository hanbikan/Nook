package com.hanbikan.nook.core.domain.repository

import kotlinx.coroutines.flow.Flow

interface AppStateRepository {
    fun getActiveUserId(): Flow<Int?>

    fun getLanguage(): Flow<String?>

    fun getVersionName(): Flow<String?>

    fun getHasMuseumGuideShown(): Flow<Boolean>

    suspend fun setActiveUserId(id: Int)

    suspend fun setLanguage(language: String)

    suspend fun setVersionName(versionName: String)

    suspend fun setHasMuseumGuideShown(flag: Boolean)
}