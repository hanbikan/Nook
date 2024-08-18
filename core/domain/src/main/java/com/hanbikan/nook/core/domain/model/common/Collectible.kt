package com.hanbikan.nook.core.domain.model.common

interface Collectible {
    val userId: Int
    val number: Int
    val name: String
    val imageUrl: String
    val renderUrl: String
    val isCollected: Boolean
    val rarity: String
    val totalCatch: Int
    val sellNook: Int
    val tankWidth: Float
    val tankLength: Float
    val catchphrases: List<String>
}

fun List<Collectible>.calculateProgress(): Float {
    return if (isEmpty()) {
        0f
    } else {
        count { it.isCollected }.toFloat() / count()
    }
}