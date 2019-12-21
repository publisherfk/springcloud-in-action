package com.heshi.ribbon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.netflix.ribbon.RibbonClients;

/**
 * @author by fukun
 */
@EnableDiscoveryClient
@SpringBootApplication
@RibbonClients({
        @RibbonClient(name = "hello-service")
})
public class RibbonApp {
    public static void main(String args[]) {
        SpringApplication.run(RibbonApp.class, args);
    }
}
