package com.hanbikan.nook.core.network.repository

import com.hanbikan.nook.core.domain.repository.RemoteCollectionRepository
import com.hanbikan.nook.core.domain.response.FishResponse
import com.hanbikan.nook.core.network.service.NookipediaService
import javax.inject.Inject

class RemoteCollectionRepositoryImpl @Inject constructor(
    private val nookipediaService: NookipediaService,
) : RemoteCollectionRepository {
    override suspend fun getAllFishes(): List<FishResponse> {
        return nookipediaService.getAllFishes()
    }
}