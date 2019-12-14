package com.heshi.feign.service;

import com.heshi.feign.client.IHelloService;
import org.springframework.stereotype.Service;

/**
 * @author by fukun
 */
@Service
public class HelloService implements IHelloService {
    @Override
    public String hello() {
        return "本地查询";
    }
}
