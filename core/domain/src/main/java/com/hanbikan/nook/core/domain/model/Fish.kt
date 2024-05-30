package com.hanbikan.nook.core.domain.model

data class Fish(
    override val id: Int = 0,
    override val userId: Int,
    override val number: Int,
    override val name: String,
    override val imageUrl: String,
    override val isCollected: Boolean,
    override val timesByMonth: Map<Int, String>,
    override val location: String,
) : MonthlyCollectible