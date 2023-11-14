package com.cocomo.library.debounce

import org.springframework.cache.Cache
import org.springframework.cache.CacheManager

class StandardDebounceExecutor(
    val cacheManager: CacheManager,
) : DebounceExecutor {

    private val cache: Cache by lazy {
        cacheManager.getCache("debounce-storage") ?: throw IllegalStateException("Cache not ready")
    }

    override fun execute(type: DebounceEventType, key: DebounceKey, f: () -> Unit): Executed =
        if (alreadyDebounced(key)) {
            println("already debounced: $key")
            Executed(false)
        } else {
            f()

            // Evict old cache
            get(type.value)?.let { oldKey -> evict(oldKey.value) }
            evict(type.value)

            // Put new cache
            put(type.value, CacheValue(key.value))
            put(key.value, CacheValue.any())

            Executed(true)
        }

    private fun alreadyDebounced(key: DebounceKey): Boolean = get(key.value) != null

    private fun get(cacheKey: String): CacheValue? = runCatching {
        cache.get(cacheKey, CacheValue::class.java)
    }.getOrElse {
        println("Failed to get cache: $cacheKey")
        null
    }

    private fun put(cacheKey: String, cacheValue: CacheValue) = runCatching {
        cache.put(cacheKey, cacheValue)
    }.getOrElse {
        println("Failed to put cache: $cacheKey")
    }

    private fun evict(cacheKey: String) = runCatching {
        cache.evict(cacheKey)
    }.getOrElse {
        println("Failed to evict cache: $cacheKey")
    }
}

data class CacheValue(
    val value: String
) {
    companion object {
        fun any() = CacheValue("1")
    }
}
