package com.github.wenqiglantz.service.demo.dataresidency.democlient;

import com.github.wenqiglantz.service.demo.dataresidency.data.DemoRequest;
import com.github.wenqiglantz.service.demo.dataresidency.data.DemoResponse;
import org.springframework.web.bind.annotation.*;
import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Mono;

@ReactiveFeignClient(
        name = "demo-client",
        url = "${demo.urls.base-url}",
        configuration = DemoClientConfig.class
)
public interface DemoClient {

    @PostMapping(value = "${demo.urls.create-demo-record-url}", headers = {"Content-Type=application/json"})
    Mono<DemoResponse> records(@RequestBody DemoRequest request);
}
