package com.cocomo.web

import com.cocomo.library.event.EventPublisher
import com.cocomo.library.event.UserRegionChangedEvent
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class TestController(
    val eventPublisher: EventPublisher
) {

    @GetMapping("users/{userId}/regions/{regionId}")
    fun test(
        @PathVariable userId: Long,
        @PathVariable regionId: Long,
    ) {
        val event = UserRegionChangedEvent(
            userId = userId,
            regionId = regionId,
        )
        eventPublisher.publish(event)
    }
}