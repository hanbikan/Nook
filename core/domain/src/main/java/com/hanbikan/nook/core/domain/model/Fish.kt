package com.hanbikan.nook.core.domain.model

import com.hanbikan.nook.core.domain.model.common.Collectible
import com.hanbikan.nook.core.domain.model.common.LocationBased
import com.hanbikan.nook.core.domain.model.common.Monthly

data class Fish(
    override val userId: Int,
    override val number: Int,
    override val name: String,
    override val imageUrl: String,
    override val renderUrl: String,
    override val isCollected: Boolean,
    override val rarity: String,
    override val totalCatch: Int,
    override val sellNook: Int,
    override val tankWidth: Float,
    override val tankLength: Float,
    override val catchphrases: List<String>,

    override val isNorth: Boolean,
    override val timesByMonthNorth: Map<Int, String>,
    override val timesByMonthSouth: Map<Int, String>,

    override val location: String,

    val shadowSize: String,
) : Collectible, Monthly, LocationBased