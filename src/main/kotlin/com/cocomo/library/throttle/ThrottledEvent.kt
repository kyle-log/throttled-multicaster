package com.cocomo.library.throttle

interface ThrottledEvent {
    val throttleKey: ThrottleKey
    val throttleValue: ThrottleValue
}

@JvmInline
value class ThrottleKey private constructor(
    val value: String
) {
    companion object {
        fun of(id: String) = ThrottleKey(value = id)
    }
}

@JvmInline
value class ThrottleValue private constructor(
    val value: String
) {
    companion object {
        fun of(key: String) = ThrottleValue(value = key)
    }
}
