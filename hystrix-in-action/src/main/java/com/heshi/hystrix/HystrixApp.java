package com.heshi.hystrix;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

/**
 * @author by fukun
 */
@EnableDiscoveryClient
@SpringCloudApplication
@EnableHystrix
@EnableHystrixDashboard
public class HystrixApp {
    public static void main(String args[]) {
        SpringApplication.run(HystrixApp.class, args);
    }
}
