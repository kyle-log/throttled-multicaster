package com.cocomo

import com.cocomo.library.throttle.*
import com.cocomo.library.event.*
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
    fun throttledExecutor() = StandardThrottleExecutor(
        cacheManager = ConcurrentMapCacheManager(),
    )

    @Bean
    fun applicationEventProcessor(
        throttleExecutor: ThrottleExecutor,
    ) = StandardApplicationEventProcessor()
        .decoratedBy { ThrottledApplicationEventProcessor(it, throttleExecutor) }

    // Do not change bean name
    @Bean("applicationEventMulticaster")
    fun throttledApplicationEventMulticaster(
        applicationEventProcessor: ApplicationEventProcessor,
    ) = CustomApplicationEventMulticaster(
        applicationEventProcessor = applicationEventProcessor,
        errorHandler = AlwaysThrowErrorHandler()
    )

    @Bean
    fun eventHandler() = EventHandler()
}
