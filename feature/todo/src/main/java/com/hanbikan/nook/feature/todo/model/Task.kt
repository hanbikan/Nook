package com.hanbikan.nook.feature.todo.model

import android.content.Context
import com.hanbikan.nook.core.domain.model.Detail
import com.hanbikan.nook.core.domain.model.Task
import com.hanbikan.nook.feature.todo.R

fun Task.Companion.createInitialTasks(
    userId: Int,
    context: Context,
): List<Task> = listOf(

    Task(
        name = context.getString(R.string.task_name1),
        userId = userId,
        isDaily = true,
    ),
    Task(
        name = context.getString(R.string.task_name2),
        userId = userId,
        isDaily = true,
    ),
    Task(
        name = context.getString(R.string.task_name3),
        userId = userId,
        isDaily = true,
        details = listOf(
            Detail(
                description = context.getString(R.string.task_detail_description3_1),
                imageId = null // TODO
            ),
            Detail(description = context.getString(R.string.task_detail_description3_2))
        )
    ),
    Task(
        name = context.getString(R.string.task_name4),
        userId = userId,
        isDaily = true,
    ),
    Task(
        name = context.getString(R.string.task_name5),
        userId = userId,
        isDaily = true,
    ),
    Task(
        name = context.getString(R.string.task_name6),
        userId = userId,
        isDaily = true,
    ),
)