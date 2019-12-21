package com.heshi.hello.provider;

import com.heshi.hello.client.IModel1Client;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author by fukun
 */
@Primary
@RestController
public class Model1ClientProvider implements IModel1Client {

    @Value("${server.port}")
    private int port;

    @Override
    public String hello() {
        return "provider" + port;
    }
}
