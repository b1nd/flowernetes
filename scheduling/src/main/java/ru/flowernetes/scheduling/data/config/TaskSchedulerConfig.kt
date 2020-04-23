package ru.flowernetes.scheduling.data.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler
import org.springframework.scheduling.config.ScheduledTaskRegistrar


@Configuration
@EnableScheduling
open class TaskSchedulerConfig {

    @Bean
    open fun threadPoolTaskScheduler(): ThreadPoolTaskScheduler {
        return ThreadPoolTaskScheduler().apply {
            poolSize = 4
            threadNamePrefix = "ThreadPoolTaskScheduler"
        }
    }

    @Bean
    open fun scheduledTaskRegistrar(threadPoolTaskScheduler: ThreadPoolTaskScheduler): ScheduledTaskRegistrar {
        return ScheduledTaskRegistrar().apply {
            setTaskScheduler(threadPoolTaskScheduler)
        }
    }
}