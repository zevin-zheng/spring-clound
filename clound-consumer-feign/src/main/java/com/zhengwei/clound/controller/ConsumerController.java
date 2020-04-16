package com.zhengwei.clound.controller;

import com.zhengwei.clound.service.FeignProductService;
import com.zhengwei.clound.service.FeignUserService;
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
    private FeignProductService feignProductService;

    @Resource
    private FeignUserService feignUserService;

    @GetMapping("/hello/{name}")
    public String index(@PathVariable("name") String name) {
        String productResult = feignProductService.hello(name);
        String userResult = feignUserService.user(name);
        return productResult + "/n" + userResult;
    }
}
