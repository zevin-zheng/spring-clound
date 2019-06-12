package com.zhengwei.clound.controller;

import com.zhengwei.clound.service.RibbonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * ${description}
 *
 * @author zhengwei 卓望信息(北京)
 * @version 1.0
 * @since 1.0
 * 2019/5/23 16:59
 **/
@RestController
public class ConsumerController {

    @Autowired
    private RibbonService ribbonService;

    @GetMapping("/hello/{name}")
    public String index(@PathVariable("name") String name){
        return ribbonService.hello(name);
    }

}
