package com.hanbikan.nook.core.network.response

import kotlinx.serialization.Serializable

@Serializable
data class Fish(
    val url: String,
    val name: String,
    val number: Int,
    val image_url: String,
    val render_url: String,
    val location: String,
    val shadow_size: String,
    val rarity: String,
    val total_catch: Int,
    val sell_nook: Int,
    val sell_cj: Int,
    val tank_width: Float,
    val tank_length: Float,
    val catchphrases: List<String>,
    val north: HemisphereAvailability,
    val south: HemisphereAvailability,
)