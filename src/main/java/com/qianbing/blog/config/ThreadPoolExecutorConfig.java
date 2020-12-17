package com.qianbing.blog.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@EnableConfigurationProperties(ThreadPoolProperties.class)
@Configuration
public class ThreadPoolExecutorConfig {
    @Bean
    public ThreadPoolExecutor threadPoolExecutor(ThreadPoolProperties threadPoolProperties){
        return new ThreadPoolExecutor(threadPoolProperties.getCoreSize(),threadPoolProperties.getMaxSize(),threadPoolProperties.getKeepTime(), TimeUnit.SECONDS,new LinkedBlockingDeque<>(100000), Executors.defaultThreadFactory(),new ThreadPoolExecutor.AbortPolicy());
    }

}
