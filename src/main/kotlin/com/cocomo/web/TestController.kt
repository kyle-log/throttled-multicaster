package com.cocomo.web

import com.cocomo.library.event.EventPublisher
import com.cocomo.library.event.UserRegionChangedEvent
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class TestController(
    val eventPublisher: EventPublisher
) {

    @GetMapping
    fun test() {
        val event = UserRegionChangedEvent(
            userId = 1,
            regionId = 2,
        )
        eventPublisher.publish(event)
        eventPublisher.publish(event)
        eventPublisher.publish(event)
    }


    @GetMapping("2")
    fun test2() {
        val event = UserRegionChangedEvent(
            userId = 5,
            regionId = 6,
        )
        eventPublisher.publish(event)
        eventPublisher.publish(event)
        eventPublisher.publish(event)
    }
}