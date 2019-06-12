package com.zhengwei.clound.controller;

import com.zhengwei.clound.service.FeignExampleService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * ${description}
 *
 * @author zhengwei 卓望信息(北京)
 * @version 1.0
 * @since 1.0
 * 2019/5/24 11:08
 **/
@RestController
public class ConsumerController {

    @Resource
    private FeignExampleService feignExampleService;

    @GetMapping("/hello/{name}")
    public String index(@PathVariable("name") String name) {
        return feignExampleService.hello(name);
    }
}
