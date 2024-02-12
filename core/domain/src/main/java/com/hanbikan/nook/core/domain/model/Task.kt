package com.hanbikan.nook.core.domain.model

data class Task(
    val id: Int = 0,
    val userId: Int,
    val isDaily: Boolean,
    override val name: String,
    override val isDone: Boolean,
    override val details: List<Detail>? = null,
) : Completable, HasDetail