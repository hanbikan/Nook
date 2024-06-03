package com.hanbikan.nook.core.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

val Context.dataStore: DataStore<Preferences> by preferencesDataStore("preferences")

class NkDataStore @Inject constructor(
    private val context: Context,
) {
    val activeUserIdFlow: Flow<Int?> = context.dataStore.data.map { it[ACTIVE_USER_ID] }
    val lastVisitedRouteFlow: Flow<String?> = context.dataStore.data.map { it[LAST_VISITED_ROUTE] }
    val languageFlow: Flow<String?> = context.dataStore.data.map { it[LANGUAGE] }
    val versionNameFlow: Flow<String?> = context.dataStore.data.map { it[VERSION_NAME] }

    suspend fun setActiveUserId(id: Int) {
        context.dataStore.edit {
            it[ACTIVE_USER_ID] = id
        }
    }

    suspend fun setLastVisitedRoute(route: String) {
        context.dataStore.edit {
            it[LAST_VISITED_ROUTE] = route
        }
    }

    suspend fun setLanguage(language: String) {
        context.dataStore.edit {
            it[LANGUAGE] = language
        }
    }

    suspend fun setVersionName(versionName: String) {
        context.dataStore.edit {
            it[VERSION_NAME] = versionName
        }
    }

    companion object {
        val ACTIVE_USER_ID = intPreferencesKey("active_user_id")
        val LAST_VISITED_ROUTE = stringPreferencesKey("last_visited_route")
        val LANGUAGE = stringPreferencesKey("language")
        val VERSION_NAME = stringPreferencesKey("version_name")
    }
}