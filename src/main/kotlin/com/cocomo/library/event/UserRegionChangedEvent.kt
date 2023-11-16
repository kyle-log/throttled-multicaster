package com.cocomo.library.event

import com.cocomo.library.throttle.ThrottleKey
import com.cocomo.library.throttle.ThrottleValue
import com.cocomo.library.throttle.ThrottledEvent

data class UserRegionChangedEvent(
    val userId: Long,
    val regionId: Long,
    // throttle information
    override val throttleKey: ThrottleKey = ThrottleKey.of("UserRegionChangedEvent(userId:$userId)"),
    override val throttleValue: ThrottleValue = ThrottleValue.of("$userId$regionId"),
) : ThrottledEvent