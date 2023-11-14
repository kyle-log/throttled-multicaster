package com.cocomo

import com.cocomo.library.event.ApplicationEventPublisherAdapter
import com.cocomo.library.debounce.DebouncedApplicationEventMulticaster
import com.cocomo.library.debounce.DebounceExecutor
import com.cocomo.library.debounce.StandardDebounceExecutor
import com.cocomo.worker.EventHandler
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.concurrent.ConcurrentMapCacheManager
import org.springframework.context.ApplicationEventPublisher
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@SpringBootApplication
class Application

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}

@Configuration
class Configuration {

    @Bean
    fun applicationEventPublisherAdapter(
        applicationEventPublisher: ApplicationEventPublisher
    ) = ApplicationEventPublisherAdapter(
        applicationEventPublisher = applicationEventPublisher
    )

    // You can change cacheManager to redis or something
    @Bean
    fun debounceStorage() = StandardDebounceExecutor(
        cacheManager = ConcurrentMapCacheManager(),
    )

    // Do not change bean name
    @Bean("applicationEventMulticaster")
    fun debouncedApplicationEventMulticaster(
        debounceExecutor: DebounceExecutor,
    ) = DebouncedApplicationEventMulticaster(
        debounceExecutor = debounceExecutor,
    )

    @Bean
    fun eventHandler() = EventHandler()
}
