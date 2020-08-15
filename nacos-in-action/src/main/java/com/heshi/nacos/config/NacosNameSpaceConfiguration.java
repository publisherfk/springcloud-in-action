package com.heshi.nacos.config;

import com.alibaba.cloud.nacos.NacosConfigProperties;
import com.alibaba.nacos.api.exception.NacosException;
import com.heshi.nacos.service.NacosNameSpaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: fukun
 * @Date: 2020/8/13 19:50
 * @since
 */
@Configuration(
        proxyBeanMethods = false
)
@ConditionalOnProperty(
        name = {"spring.cloud.nacos.config.enabled"},
        matchIfMissing = true
)
public class NacosNameSpaceConfiguration {

    @Autowired
    public NacosConfigProperties nacosConfigProperties;

    @Bean
    @ConditionalOnMissingBean
    public NacosNameSpaceService nacosNameSpaceService(NacosConfigProperties nacosConfigProperties) throws NacosException {
        return new NacosNameSpaceService(nacosConfigProperties.assembleConfigServiceProperties());
    }
}