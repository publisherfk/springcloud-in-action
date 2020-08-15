package com.heshi.admin.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: fukun
 * @Date: 2020/7/8 19:54
 * @since
 */
@RestController
@RequestMapping("/base/controller")
public class HelloController {
    private Logger log = LoggerFactory.getLogger(LogbackController.class);

    @Value("${com.heshi}")
    private String heshi;

    @GetMapping("/hello")
    public String hello() {
        log.info("============info===============");
        log.error("============error===============");
        log.error(heshi);
        return "hello";
    }
}
