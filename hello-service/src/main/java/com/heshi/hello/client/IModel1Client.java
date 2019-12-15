package com.heshi.hello.client;

import com.heshi.hello.fallback.Model1ClientFallBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author by fukun
 */
@FeignClient(name = "hello-service", fallback = Model1ClientFallBack.class, primary = false)
public interface IModel1Client {
    String API_PREFIX = "/v1";

    @GetMapping(API_PREFIX + "/hello")
    public String hello();
}
