package com.hanbikan.nook.core.domain.model.common

import kotlinx.serialization.Serializable

@Serializable
data class Detail(
    val description: String,
    val imageId: Int? = null, // deprecated
    val imageUrl: String? = null
)