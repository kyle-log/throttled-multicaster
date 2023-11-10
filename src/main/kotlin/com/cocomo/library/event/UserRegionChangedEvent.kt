package com.cocomo.library.event

import com.cocomo.library.debounce.DebounceEventType
import com.cocomo.library.debounce.DebounceKey
import com.cocomo.library.debounce.DebouncedEvent

data class UserRegionChangedEvent(
    val userId: Long,
    val regionId: Long,
    // debounce information
    override val debounceEventType: DebounceEventType = DebounceEventType.of("UserRegionChangedEvent"),
    override val debounceKey: DebounceKey = DebounceKey.of("$userId$regionId"),
) : DebouncedEvent