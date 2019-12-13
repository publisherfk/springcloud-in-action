package com.heshi.hystrix.controller;

import com.heshi.hystrix.service.HystrixHelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author by fukun
 */
@RestController
@RequestMapping("/base")
public class ConsumerController {
    @Autowired
    HystrixHelloService helloService;

    @GetMapping("/ribbon-consumer")
    public String helloConsumer() {
        return helloService.helloService();
    }
}
