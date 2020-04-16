package com.zhengwei.clound.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * ${description}
 *
 * @author zhengwei 卓望信息(北京)
 * @version 1.0
 * @since 1.0
 * 2019/5/22 11:45
 **/
@RestController
public class ProducerController {

    @Value("${server.port}")
    String port;

    @GetMapping("/hello")
    public String hello(@RequestParam String name) {
        return "product2 "+name+"，from "+ port+ " this is new world";
    }
}
