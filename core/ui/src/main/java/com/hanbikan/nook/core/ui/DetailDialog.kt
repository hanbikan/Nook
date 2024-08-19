package com.hanbikan.nook.core.ui

import androidx.compose.runtime.Composable
import com.hanbikan.nook.core.designsystem.component.NkSequentialDialog
import com.hanbikan.nook.core.domain.model.common.Detail

@Composable
fun DetailDialog(
    visible: Boolean,
    detailsToShow: List<Detail>,
    hideDetailDialog: () -> Unit,
) {
    NkSequentialDialog(
        visible = visible,
        descriptions = detailsToShow.map { it.description },
        imageUrls = detailsToShow.map { detail -> detail.imageUrl },
        onDismissRequest = hideDetailDialog,
        onConfirmation = hideDetailDialog,
        hasOnlyConfirmationButton = true,
    )
}