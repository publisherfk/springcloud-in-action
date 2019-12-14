package com.heshi.feign;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author by fukun
 */
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
@ComponentScan({"com.heshi.feign", "com.heshi.hello"})
public class FeignApp {
    public static void main(String args[]) {
        SpringApplication.run(FeignApp.class, args);
    }
}
