package com.hanbikan.nook.core.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

enum class DragValue { Start, Center, End }

data class DragActions(
    val startAction: DragAction,
    val endAction: DragAction,
) {
    companion object {
        fun withSameActions(action: DragAction) = DragActions(
            startAction = action,
            endAction = action
        )
    }
}

data class DragAction(
    val backgroundColor: Color,
    val iconImageVector: ImageVector,
    val iconTint: Color,
    val onClick: () -> Unit
) {
    companion object {
        fun deleteAction(onClick: () -> Unit) = DragAction(
            backgroundColor = Color.Red,
            iconImageVector = Icons.Default.Delete,
            iconTint = Color.White,
            onClick = onClick
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
fun <T> AnchoredDraggableState<T>.dragToCenter() {
    dispatchRawDelta(-offset)
}