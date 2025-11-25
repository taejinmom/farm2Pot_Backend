package com.farm2pot.scription.controller;

import com.farm2pot.common.http.response.ApiResponse;
import com.farm2pot.scription.controller.dto.SubscriptionRequest;
import com.farm2pot.scription.service.SubscriptionService;
import com.farm2pot.scription.service.dto.SubscriptionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/subs")
@RequiredArgsConstructor
public class SubscriptionController {
    private final SubscriptionService subscriptionService;

    @PostMapping("/subcription")
    public SubscriptionResponse createSubscribe(@RequestBody SubscriptionRequest request) {
        return subscriptionService.createSubscribe(request);
    }

    @GetMapping("/test")
    public <T> T test() {
        return subscriptionService.test();
    }

}
