package com.hanbikan.nook.core.domain.model

data class Bug(
    override val name: String,
    override val imageUrl: String,
    override val isCollected: Boolean,
    override val timesByMonth: Map<Int, String>,
    override val location: String,
) : MonthlyCollectible