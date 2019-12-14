package com.heshi.hello.provider;

import com.heshi.hello.client.IModel1Client;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author by fukun
 */
@Primary
@RestController
public class Model1ClientProvider implements IModel1Client {
    @Override
    public String hello() {
        return "provider";
    }
}
