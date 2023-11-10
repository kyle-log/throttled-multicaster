package com.cocomo.library.debounce

interface DebounceStorage {
    fun alreadyDebounced(key: DebounceKey): Boolean
    fun debounce(type: DebounceEventType, key: DebounceKey)
}

fun <T> DebounceStorage.debounceIfNotYet(
    type: DebounceEventType,
    key: DebounceKey,
    block: () -> T,
) = if (alreadyDebounced(key)) {
    // do nothing
    println("already debounced")
} else {
    block()
    debounce(type, key)
    println("debounced")
}