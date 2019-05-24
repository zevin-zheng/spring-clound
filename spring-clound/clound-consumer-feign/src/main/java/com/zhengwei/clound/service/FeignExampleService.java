package com.zhengwei.clound.service;

import com.zhengwei.clound.service.impl.FeignServiceHystrix;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 修改FeignClient增加fallback熔断处理
 *
 * @author zhengwei 卓望信息(北京)
 * @version 1.0
 * @since 1.0
 * 2019/5/24 11:07
 **/
@FeignClient(value = "product-server",fallback = FeignServiceHystrix.class)
public interface FeignExampleService {

    @GetMapping("hello")
    public String hello(@RequestParam(value = "name") String name);
}
