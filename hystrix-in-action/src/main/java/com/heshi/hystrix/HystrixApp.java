package com.heshi.hystrix;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;

/**
 * @author by fukun
 */
@SpringCloudApplication
public class HystrixApp {
    public static void main(String args[]) {
        SpringApplication.run(HystrixApp.class, args);
    }
}
