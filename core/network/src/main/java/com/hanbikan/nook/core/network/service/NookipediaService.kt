package com.hanbikan.nook.core.network.service

import com.hanbikan.nook.core.domain.response.BugResponse
import com.hanbikan.nook.core.domain.response.FishResponse
import com.hanbikan.nook.core.domain.response.SeaCreatureResponse
import retrofit2.http.GET

interface NookipediaService {

    @GET("/nh/fish")
    suspend fun getAllFishes(): List<FishResponse>


    @GET("/nh/bugs")
    suspend fun getAllBugs(): List<BugResponse>

    @GET("/nh/sea")
    suspend fun getAllSeaCreatures(): List<SeaCreatureResponse>
}