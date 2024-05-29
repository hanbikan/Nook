package com.hanbikan.nook.core.common

inline fun <T1, T2> executeIfBothNonNull(a: T1?, b: T2?, block: (T1, T2) -> Any) {
    if (a != null && b != null) {
        block(a, b)
    }
}

fun <T1, T2> Map<out T1, T2>.forEachIndexed(action: (Int, Pair<T1, T2>) -> Unit) {
    var index = 0
    forEach { (t1, t2) ->
        action(index, Pair(t1, t2))
        index += 1
    }
}