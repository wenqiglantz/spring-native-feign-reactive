package com.github.wenqiglantz.service.demo.dataresidency.functions;

import com.github.wenqiglantz.service.demo.dataresidency.data.DemoRequest;
import com.github.wenqiglantz.service.demo.dataresidency.data.DemoResponse;
import com.github.wenqiglantz.service.demo.dataresidency.democlient.DemoClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class LambdaFunctions {

    private final DemoClient demoClient;

    @Bean
    public Function<DemoRequest, Mono<DemoResponse>> records() {
        return (request) -> {
            return demoClient.records(request);
        };
    }
}
