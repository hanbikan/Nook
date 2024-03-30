package com.hanbikan.nook.core.domain.model

interface Collectible {
    val name: String
    val imageUrl: String
    val isCollected: Boolean
}

fun List<Collectible>.calculateProgress(): Float {
    return if (isEmpty()) {
        0f
    } else {
        count { it.isCollected }.toFloat() / count()
    }
}