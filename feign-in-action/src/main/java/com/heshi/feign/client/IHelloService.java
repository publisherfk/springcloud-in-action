package com.heshi.feign.client;

import com.heshi.feign.service.HelloService;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author by fukun
 */
@FeignClient(value = "hello-service", fallback = HelloService.class)
public interface IHelloService {
    String API_PREFIX = "/v1";

    @RequestMapping(API_PREFIX + "/hello")
    String hello();
}
