package com.heshi.config.center.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: fukun
 * @Date: 2020/5/15 16:38
 * @since
 */
@Configuration
@ConfigurationProperties
@RefreshScope
public class ConfigUtil {
    @Value("${PATROL.userName:}")
    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
