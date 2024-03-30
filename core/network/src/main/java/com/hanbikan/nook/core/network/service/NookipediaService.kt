package com.hanbikan.nook.core.network.service

import com.hanbikan.nook.core.domain.response.FishResponse
import retrofit2.http.GET

interface NookipediaService {

    @GET("/nh/fish")
    suspend fun getAllFishes(): List<FishResponse>
}