package com.heshi.nacos.controller;

import com.alibaba.nacos.api.exception.NacosException;
import com.heshi.nacos.service.NacosNameSpaceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: fukun
 * @Date: 2020/8/13 19:56
 * @since
 */
@RestController
@RequestMapping("/namespace")
@RefreshScope
public class NacosNameSpaceController {
    Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private NacosNameSpaceService nameSpaceService;

    @RequestMapping("/add")
    public void add(String customNamespaceId, String namespaceName, String namespaceDesc) {
        try {
            nameSpaceService.publishNamespace(customNamespaceId, namespaceName, namespaceDesc);
        } catch (NacosException e) {
            logger.error(e.getMessage(), e);
        }
    }

    @RequestMapping("/delete")
    public void delete(String customNamespaceId) {
        try {
            nameSpaceService.removeNamespace(customNamespaceId);
        } catch (NacosException e) {
            logger.error(e.getMessage(), e);
        }
    }

    @RequestMapping("/get")
    public String get(String customNamespaceId) {
        String content = null;
        try {
            content = nameSpaceService.getNamespace(customNamespaceId, 1000L);
        } catch (NacosException e) {
            logger.error(e.getMessage(), e);
        }
        return content;
    }
}
