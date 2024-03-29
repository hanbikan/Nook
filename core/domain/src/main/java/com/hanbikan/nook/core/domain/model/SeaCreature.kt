package com.hanbikan.nook.core.domain.model

data class SeaCreature(
    override val name: String,
    override val imageUrl: String,
    override val isCollected: Boolean,
    override val timesByMonth: Map<Int, String>,
) : Collectable, TimeSensitive