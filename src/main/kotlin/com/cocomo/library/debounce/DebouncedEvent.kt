package com.cocomo.library.debounce

interface DebouncedEvent {
    val debounceGroup: DebounceGroup
    val debounceKey: DebounceKey
}

@JvmInline
value class DebounceGroup private constructor(
    val value: String
) {
    companion object {
        fun of(id: String) = DebounceGroup(value = id)
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
