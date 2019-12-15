package com.heshi.hello.controller;

import com.heshi.hello.config.HelloServerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author by fukun
 */
@RestController
public class HelloController {
    Logger logger = LoggerFactory.getLogger(HelloController.class);
    @Autowired
    private Registration registration;
    @Autowired
    private DiscoveryClient client;
    @Autowired
    private HelloServerConfig serverConfig;

    @GetMapping("/hello")
    public String hello() throws InterruptedException {
        ServiceInstance serviceInstance = getServiceInstance();
        int sleepTime = new Random().nextInt(6000);
        logger.info("sleepTime:{}", sleepTime);
        Thread.sleep(sleepTime);
        logger.info("/hello, host:{}, service_id:{}", serviceInstance.getHost(), serviceInstance.getServiceId());
        return "hello";
    }

    @GetMapping("/hello2")
    public String hello2() throws InterruptedException {
        ServiceInstance serviceInstance = getServiceInstance();
        int sleepTime = new Random().nextInt(1800);
        logger.info("sleepTime:{}", sleepTime);
        Thread.sleep(sleepTime);
        logger.info("/hello2, host:{}, service_id:{}", serviceInstance.getHost(), serviceInstance.getServiceId());
        return "hello2";
    }

    public ServiceInstance getServiceInstance() {
        List<ServiceInstance> list = client.getInstances(registration.getServiceId());
        if (list != null && list.size() > 0) {
            AtomicReference<ServiceInstance> serviceInstance = new AtomicReference<>();
            list.forEach(itm -> {
                if (itm.getPort() == serverConfig.getServerPort()) {
                    serviceInstance.set(itm);
                }
            });
            return serviceInstance.get();
        }
        return null;
    }
}
