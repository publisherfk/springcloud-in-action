package com.heshi.hystrix.controller;

import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/hystrix")
@DefaultProperties(defaultFallback = "defaultFallback")
public class HystrixController {
    @Autowired
    private RestTemplate restTemplate;

    @HystrixCommand(commandProperties = {
            @HystrixProperty(
                    name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000"
            )
    })
    @GetMapping("/demo1")
    public String demo1() {
        restTemplate.getForObject("http://hello-service/hello", String.class);
        return "demo1";
    }

    @HystrixCommand(commandProperties = {
            @HystrixProperty(name = "circuitBreaker.enabled", value = "true"),
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "20"),
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000"),
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50")
    })
    @GetMapping("/demo2")
    public String demo2() {
        restTemplate.getForObject("http://hello-service/hello2", String.class);
        return "demo1";
    }

    public String defaultFallback() {
        return "服务已经失联";
    }
}
