package com.example.searchplace.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class EventListenerAsyncConfig {

    @Qualifier("eventListenerThreadExecutor")
    @Bean
    public ThreadPoolTaskExecutor threadExecutor() {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize(5); // 기본 thread 수
        threadPoolTaskExecutor.setMaxPoolSize(5); //최대 thread 수
        threadPoolTaskExecutor.setQueueCapacity(3); //기본 thread량 초과시 queue에 쌓이게
        threadPoolTaskExecutor.setThreadNamePrefix("eventListenerThreadPool-");
        threadPoolTaskExecutor.setAwaitTerminationSeconds(20);
        threadPoolTaskExecutor.setWaitForTasksToCompleteOnShutdown(true); //graceful shutdown
        threadPoolTaskExecutor.initialize();
        return threadPoolTaskExecutor;
    }

}
