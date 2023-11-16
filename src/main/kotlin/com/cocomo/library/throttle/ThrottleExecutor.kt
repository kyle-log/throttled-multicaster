package com.cocomo.library.throttle

interface ThrottleExecutor {
    fun execute(key: ThrottleKey, value: ThrottleValue, f: () -> Unit): Executed
}

@JvmInline
value class Executed(val value: Boolean)
