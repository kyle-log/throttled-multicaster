package com.cocomo.library.debounce

interface DebouncedEvent {
    val debounceEventType: DebounceEventType
    val debounceKey: DebounceKey
}

@JvmInline
value class DebounceEventType private constructor(
    val value: String
) {
    companion object {
        fun of(id: String) = DebounceEventType(value = id)
    }
}

@JvmInline
value class DebounceKey private constructor(
    val value: String
) {
    companion object {
        fun of(key: String) = DebounceKey(value = key)
    }
}
