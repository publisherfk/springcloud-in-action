package com.heshi.feign.controller;

import com.heshi.feign.client.IModel1Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author by fukun
 */
@RestController
public class Model1ClientController {
    @Autowired
    private IModel1Client model1Client;

    @GetMapping("/feign-Model1-consumer")
    public String model1ClientConsumer() {
        return model1Client.hello();
    }
}
