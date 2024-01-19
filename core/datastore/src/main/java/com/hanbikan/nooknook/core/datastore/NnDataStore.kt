package com.hanbikan.nooknook.core.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

val Context.dataStore: DataStore<Preferences> by preferencesDataStore("preferences")

class NnDataStore @Inject constructor(
    context: Context
) {
    val activeUserIdFlow: Flow<Int?> = context.dataStore.data.map { it[ACTIVE_USER_ID] }

    companion object {
        val ACTIVE_USER_ID = intPreferencesKey("active_user_id")
    }
}