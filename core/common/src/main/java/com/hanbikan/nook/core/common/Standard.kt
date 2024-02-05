package com.hanbikan.nook.core.common

inline fun <T1, T2> executeIfBothNonNull(a: T1?, b: T2?, block: (T1, T2) -> Any) {
    if (a != null && b != null) {
        block(a, b)
    }
}