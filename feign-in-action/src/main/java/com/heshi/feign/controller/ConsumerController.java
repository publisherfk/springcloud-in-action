package com.heshi.feign.controller;

import com.heshi.feign.client.IHelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author by fukun
 */
@RestController
public class ConsumerController {
    @Autowired
    IHelloService helloService;

    @GetMapping("/feign-consumer")
    public String helloConsumer() {
        return helloService.hello();
    }
}
