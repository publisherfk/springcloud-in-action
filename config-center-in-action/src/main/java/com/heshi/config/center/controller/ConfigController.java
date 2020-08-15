package com.heshi.config.center.controller;

import com.heshi.config.center.util.ConfigUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: fukun
 * @Date: 2020/5/15 16:39
 * @since
 */
@RestController
@RequestMapping("/base")
public class ConfigController {

    @Autowired
    private ConfigUtil configUtil;

    @GetMapping("/test")
    public String test() {
        return configUtil.getUserName();
    }
}
