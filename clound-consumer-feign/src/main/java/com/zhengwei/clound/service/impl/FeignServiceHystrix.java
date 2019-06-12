package com.zhengwei.clound.service.impl;

import com.zhengwei.clound.service.FeignExampleService;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 实现feign接口用于熔断处理
 *
 * @author zhengwei 卓望信息(北京)
 * @version 1.0
 * @since 1.0
 * 2019/5/24 11:34
 **/
@Component
public class FeignServiceHystrix implements FeignExampleService {

    @Override
    public String hello(@RequestParam(value = "name") String name) {
        return "sorry "+name+"，service has fail!";
    }
}
