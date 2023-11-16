package com.cocomo.library.throttle

import org.springframework.cache.Cache
import org.springframework.cache.CacheManager

class StandardThrottleExecutor(
    val cacheManager: CacheManager,
) : ThrottleExecutor {

    private val cache: Cache by lazy {
        cacheManager.getCache("throttle-storage") ?: throw IllegalStateException("Cache not ready")
    }

    override fun execute(key: ThrottleKey, value: ThrottleValue, f: () -> Unit): Executed =
        if (alreadyThrottled(key, value)) {
            println("Skipped: $value")
            Executed(false)
        } else {
            f()

            evict(key.value)
            put(key.value, CacheValue(value.value))

            Executed(true)
        }

    private fun alreadyThrottled(key: ThrottleKey, value: ThrottleValue): Boolean {
        return get(key.value)?.value == value.value
    }

    private fun get(cacheKey: String): CacheValue? = runCatching {
        cache.get(cacheKey, CacheValue::class.java)
    }.getOrNull()

    private fun put(cacheKey: String, cacheValue: CacheValue) = runCatching {
        cache.put(cacheKey, cacheValue)
    }.getOrNull()

    private fun evict(cacheKey: String) = runCatching {
        cache.evict(cacheKey)
    }.getOrNull()
}

data class CacheValue(
    val value: String
) {
    companion object {
        fun any() = CacheValue("1")
    }
}
