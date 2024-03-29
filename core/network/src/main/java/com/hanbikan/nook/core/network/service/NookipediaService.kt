package com.hanbikan.nook.core.network.service

import com.hanbikan.nook.core.network.response.FishResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET

interface NookipediaService {

    @GET("/nh/fish")
    fun getAllFishes(): Flow<List<FishResponse>>
}