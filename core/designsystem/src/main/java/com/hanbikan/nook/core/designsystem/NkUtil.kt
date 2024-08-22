package com.hanbikan.nook.core.designsystem

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp

fun getAlphaByEnabled(enabled: Boolean): Float {
    return if (enabled) {
        NkConst.AlphaEnabled
    } else {
        NkConst.AlphaDisabled
    }
}

@Composable
private fun Float.toDp(): Dp {
    return with(LocalDensity.current) { this@toDp.toDp() }
}