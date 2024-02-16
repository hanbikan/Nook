package com.hanbikan.nook.core.domain.model

data class Task(
    val id: Int = 0,
    val userId: Int,
    val isDaily: Boolean,
    val isVisible: Boolean = true,
    override val name: String,
    override val isDone: Boolean = false,
    override val details: List<Detail>? = null,
) : Completable, HasDetail