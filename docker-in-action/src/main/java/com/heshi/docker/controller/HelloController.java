package com.heshi.docker.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: fukun
 * @Date: 2020/7/4 18:35
 * @since
 */
@RestController
@RequestMapping("/base")
public class HelloController {

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }
}
