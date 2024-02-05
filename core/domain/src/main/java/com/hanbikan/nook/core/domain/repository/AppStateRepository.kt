package com.hanbikan.nook.core.domain.repository

import kotlinx.coroutines.flow.Flow

interface AppStateRepository {
    fun getActiveUserId(): Flow<Int?>

    fun getLastVisitedRoute(): Flow<String?>

    fun getTutorialDay(): Flow<Int>

    suspend fun setActiveUserId(id: Int)

    suspend fun setLastVisitedRoute(route: String)

    suspend fun setTutorialDay(tutorialDay: Int)
}