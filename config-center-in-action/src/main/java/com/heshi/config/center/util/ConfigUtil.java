package com.heshi.config.center.util;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: fukun
 * @Date: 2020/5/15 16:38
 * @since
 */
@Configuration
@ConfigurationProperties(prefix = "integration")
public class ConfigUtil {
    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
