package com.hanbikan.nook.core.designsystem

fun getAlphaByEnabled(enabled: Boolean): Float {
    return if (enabled) {
        NkConst.AlphaEnabled
    } else {
        NkConst.AlphaDisabled
    }
}