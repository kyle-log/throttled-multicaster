package com.cocomo.library.debounce

import com.cocomo.library.event.ApplicationEventProcessor
import org.springframework.context.ApplicationEvent
import org.springframework.context.PayloadApplicationEvent
import org.springframework.context.event.GenericApplicationListener

class DebouncedApplicationEventProcessor(
    private val delegate: ApplicationEventProcessor,
    private val debounceExecutor: DebounceExecutor,
) : ApplicationEventProcessor {

    override fun process(listener: GenericApplicationListener, event: ApplicationEvent) {
        runCatching {
            val payload = (event as PayloadApplicationEvent<*>).payload as DebouncedEvent
            debounceExecutor.execute(payload.debounceGroup, payload.debounceKey) {
                delegate.process(listener, event)
            }
        }.getOrElse {
            delegate.process(listener, event)
        }
    }
}