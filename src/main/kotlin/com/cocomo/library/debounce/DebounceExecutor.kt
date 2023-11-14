package com.cocomo.library.debounce

interface DebounceExecutor {
    fun execute(type: DebounceEventType, key: DebounceKey, f: () -> Unit): Executed
}

@JvmInline
value class Executed(val executed: Boolean)
