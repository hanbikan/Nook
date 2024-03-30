package com.hanbikan.nook.core.domain.repository

import com.hanbikan.nook.core.domain.response.FishResponse

interface RemoteCollectionRepository {

    suspend fun getAllFishes(): List<FishResponse>
}