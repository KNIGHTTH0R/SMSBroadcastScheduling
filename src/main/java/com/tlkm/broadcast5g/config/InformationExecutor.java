package com.tlkm.broadcast5g.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class InformationExecutor {
    @Value("${thread.core-pool}")
    private int corePoolSize;

    @Value("${thread.max-pool}")
    private int maxPoolSize;

    @Value("${queue.capacity}")
    private int queueCapacity;


    @Bean
    @Qualifier("offeringExecutor")
    public ThreadPoolTaskExecutor threadPoolTaskExecutor() {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize(corePoolSize);
        threadPoolTaskExecutor.setMaxPoolSize(maxPoolSize);
        threadPoolTaskExecutor.setQueueCapacity(queueCapacity);
        //threadPoolTaskExecutor.setKeepAliveSeconds(threadTimeout);
        threadPoolTaskExecutor.setThreadNamePrefix("SMSInformation-");


        return threadPoolTaskExecutor;
    }


}
