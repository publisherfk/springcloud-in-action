package com.heshi.feign.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author by fukun
 */
@FeignClient(name = "hello-service")
public interface IModel1Client {
    String API_PREFIX = "/v1";

    @GetMapping(API_PREFIX + "/hello")
    public String hello();
}
