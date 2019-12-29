package com.heshi.ribbon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

/**
 * @author by fukun
 */
@RestController
public class ClientController {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private LoadBalancerClient loadBalancerClient;
    @Value("${server.provider.name}")
    private String serverProviderName;

    @GetMapping("")
    public String index() {
        ResponseEntity<String> responseEntity = restTemplate.getForEntity("http://" + serverProviderName + "/v1/hello", String.class);
        return responseEntity.getBody();
    }

    @GetMapping("/hello1")
    public String hello1() throws IOException {
        ServiceInstance serviceInstance = loadBalancerClient.choose(serverProviderName);
        return loadBalancerClient.execute(serverProviderName, serviceInstance, instance -> {
            String host = instance.getHost();
            int port = instance.getPort();
            String urlMethod = "http";
            if (instance.isSecure()) {
                urlMethod = "https";
            }
            String url = urlMethod + "://" + host + ":" + port + "/v1/hello";
            RestTemplate restTemplateInner = new RestTemplate();
            return restTemplateInner.getForEntity(url, String.class).getBody();
        });
    }
}
