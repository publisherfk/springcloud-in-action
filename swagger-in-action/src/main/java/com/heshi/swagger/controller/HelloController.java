package com.heshi.swagger.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: fukun
 * @Date: 2020/7/8 19:54
 * @since
 */
@Api
@RestController
@RequestMapping("/base/controller")
public class HelloController {

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }
}
