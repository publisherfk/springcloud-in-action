package com.heshi.hystrix.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author by fukun
 */
@Service
public class HystrixHelloService {
    Logger logger = LoggerFactory.getLogger(HystrixHelloService.class);
    @Autowired
    RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "helloFallback")
    public String helloService() {
        long startTime = System.currentTimeMillis();
        String result = restTemplate.getForEntity("http://hello-service/hello", String.class).getBody();
        logger.info("Spend time : {}", (System.currentTimeMillis() - startTime));
        return result;
    }

    public String helloFallback() {
        return "error";
    }
}
