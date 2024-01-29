package com.hanbikan.nook.core.database.translator

import com.hanbikan.nook.core.database.entity.TaskEntity
import com.hanbikan.nook.core.domain.model.Task

fun TaskEntity.toDomain(): Task {
    return Task(id, userId, name, isDone)
}

fun Task.toData(): TaskEntity {
    return TaskEntity(id, userId, name, isDone)
}