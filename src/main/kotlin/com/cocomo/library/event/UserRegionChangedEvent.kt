package com.cocomo.library.event

import com.cocomo.library.debounce.DebounceGroup
import com.cocomo.library.debounce.DebounceKey
import com.cocomo.library.debounce.DebouncedEvent

data class UserRegionChangedEvent(
    val userId: Long,
    val regionId: Long,
    // debounce information
    override val debounceGroup: DebounceGroup = DebounceGroup.of("UserRegionChangedEvent(userId:$userId)"),
    override val debounceKey: DebounceKey = DebounceKey.of("$userId$regionId"),
) : DebouncedEvent