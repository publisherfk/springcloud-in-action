package com.heshi.ribbon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author by fukun
 */
@RestController
public class ClientController {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${server.provider.name}")
    private String serverProviderName;

    @GetMapping("")
    public String index() {
        ResponseEntity<String> responseEntity = restTemplate.getForEntity("http://" + serverProviderName + "/v1/hello", String.class);
        return responseEntity.getBody();
    }
}
