package com.hanbikan.nook.core.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.hanbikan.nook.core.designsystem.component.NkSequentialDialog
import com.hanbikan.nook.core.domain.model.Detail

@Composable
fun DetailDialog(
    visible: Boolean,
    detailsToShow: List<Detail>,
    hideDetailDialog: () -> Unit,
) {
    NkSequentialDialog(
        visible = visible,
        descriptions = detailsToShow.map { it.description },
        painters = detailsToShow.map { detail ->
            detail.imageId?.let { painterResource(id = it) }
        },
        onDismissRequest = hideDetailDialog,
        onConfirmation = hideDetailDialog,
        hasOnlyConfirmationButton = true,
    )
}