package com.heshi.hello.config;

import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @author by fukun
 */
@Component
public class HelloServerConfig implements ApplicationListener<WebServerInitializedEvent> {
    private int serverPort;

    public int getServerPort() {
        return serverPort;
    }

    @Override
    public void onApplicationEvent(WebServerInitializedEvent event) {
        serverPort = event.getWebServer().getPort();
    }
}
