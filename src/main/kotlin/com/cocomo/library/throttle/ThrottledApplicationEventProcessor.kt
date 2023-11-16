package com.cocomo.library.throttle

import com.cocomo.library.event.ApplicationEventProcessor
import org.springframework.context.ApplicationEvent
import org.springframework.context.PayloadApplicationEvent
import org.springframework.context.event.GenericApplicationListener

class ThrottledApplicationEventProcessor(
    private val delegate: ApplicationEventProcessor,
    private val throttleExecutor: ThrottleExecutor,
) : ApplicationEventProcessor {

    override fun process(listener: GenericApplicationListener, event: ApplicationEvent) {
        runCatching {
            val payload = (event as PayloadApplicationEvent<*>).payload as ThrottledEvent
            throttleExecutor.execute(payload.throttleKey, payload.throttleValue) {
                delegate.process(listener, event)
            }
        }.getOrElse {
            delegate.process(listener, event)
        }
    }
}