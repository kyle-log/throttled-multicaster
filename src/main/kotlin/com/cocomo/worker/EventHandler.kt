package com.cocomo.worker

import com.cocomo.library.event.UserRegionChangedEvent
import org.springframework.context.event.EventListener

class EventHandler {

    @EventListener
    fun handle(event: UserRegionChangedEvent) {
        println("Executed: ${event.throttleValue}")
    }
}