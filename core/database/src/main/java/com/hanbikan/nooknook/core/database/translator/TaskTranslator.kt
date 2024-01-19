package com.hanbikan.nooknook.core.database.translator

import com.hanbikan.nooknook.core.database.entity.TaskEntity
import com.hanbikan.nooknook.core.domain.model.Task

fun TaskEntity.toDomain(): Task {
    return Task(id, userId, name, isDone)
}

fun Task.toData(): TaskEntity {
    return TaskEntity(id, userId, name, isDone)
}