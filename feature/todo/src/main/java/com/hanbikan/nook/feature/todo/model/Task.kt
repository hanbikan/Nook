package com.hanbikan.nook.feature.todo.model

import android.content.Context
import com.hanbikan.nook.core.domain.model.common.Detail
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
                imageUrl = "https://firebasestorage.googleapis.com/v0/b/acnh-1be21.appspot.com/o/shine_spot.png?alt=media&token=af8e36c8-db35-4654-b834-f4a94e3374bf"
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