package com.cocomo.library.debounce

interface DebounceExecutor {
    fun execute(type: DebounceGroup, key: DebounceKey, f: () -> Unit): Executed
}

@JvmInline
value class Executed(val executed: Boolean)
