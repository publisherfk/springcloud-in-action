package com.heshi.hello.fallback;

import com.heshi.hello.client.IModel1Client;
import org.springframework.stereotype.Component;

/**
 * @author by fukun
 */
@Component
public class Model1ClientFallBack implements IModel1Client {
    @Override
    public String hello() {
        return "fallback";
    }
}
