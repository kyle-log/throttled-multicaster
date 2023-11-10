package com.cocomo

import com.cocomo.library.event.ApplicationEventPublisherAdapter
import com.cocomo.library.debounce.DebouncedApplicationEventMulticaster
import com.cocomo.library.debounce.DebounceStorage
import com.cocomo.library.debounce.StandardDebounceStorage
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
    fun debounceStorage() = StandardDebounceStorage(
        cacheManager = ConcurrentMapCacheManager(),
    )

    // Do not change bean name
    @Bean("applicationEventMulticaster")
    fun debouncedApplicationEventMulticaster(
        debounceStorage: DebounceStorage,
    ) = DebouncedApplicationEventMulticaster(
        debounceStorage = debounceStorage,
    )

    @Bean
    fun eventHandler() = EventHandler()
}
