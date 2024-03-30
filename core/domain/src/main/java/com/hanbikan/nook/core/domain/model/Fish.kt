package com.hanbikan.nook.core.domain.model

data class Fish(
    val id: Int = 0,
    val userId: Int,
    override val name: String,
    val number: Int,
    override val imageUrl: String,
    override val timesByMonth: Map<Int, String>,
    override val isCollected: Boolean,
) : MonthlyCollectible