package com.github.wenqiglantz.service.demo.democlient;

import feign.RetryableException;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Indexed;
import reactivefeign.ReactiveOptions;
import reactivefeign.client.ReactiveHttpResponse;
import reactivefeign.client.log.DefaultReactiveLogger;
import reactivefeign.client.log.ReactiveLoggerListener;
import reactivefeign.client.statushandler.ReactiveStatusHandler;
import reactivefeign.client.statushandler.ReactiveStatusHandlers;
import reactivefeign.retry.BasicReactiveRetryPolicy;
import reactivefeign.retry.ReactiveRetryPolicy;
import reactivefeign.webclient.WebReactiveOptions;

import java.time.Clock;
import java.time.Instant;
import java.util.*;
import java.util.function.BiFunction;

@Configuration
@ConfigurationProperties
@Indexed
@Data
@Slf4j
public class DemoClientConfig {

    @Value("${demo.http-client.read-timeout}")
    private int readTimeout;

    @Value("${demo.http-client.write-timeout}")
    private int writeTimeout;

    @Value("${demo.http-client.connect-timeout}")
    private int connectTimeout;

    @Value("${demo.http-client.response-timeout}")
    private int responseTimeout;

    @Value("${demo.retry.max-retry}")
    private int maxRetry;

    @Value("${demo.retry.retry-interval}")
    private int retryInterval;

    @Bean
    public ReactiveLoggerListener loggerListener() {
        return new DefaultReactiveLogger(Clock.systemUTC(), LoggerFactory.getLogger(DemoClient.class.getName()));
    }

    @Bean
    public ReactiveRetryPolicy reactiveRetryPolicy() {
        return BasicReactiveRetryPolicy.retryWithBackoff(maxRetry, retryInterval);
    }

    @Bean
    public ReactiveStatusHandler reactiveStatusHandler() {
        return ReactiveStatusHandlers.throwOnStatus(
                (status) -> (status == 500),
                errorFunction());
    }

    private BiFunction<String, ReactiveHttpResponse, Throwable> errorFunction() {
        return (methodKey, response) -> {
            return new RetryableException(response.status(), "", null, Date.from(Instant.EPOCH), null);
        };
    }

    @Bean
    public ReactiveOptions reactiveOptions() {
        return new WebReactiveOptions.Builder()
                .setReadTimeoutMillis(readTimeout)
                .setWriteTimeoutMillis(writeTimeout)
                .setResponseTimeoutMillis(responseTimeout)
                .setConnectTimeoutMillis(connectTimeout)
                .build();
    }
}
