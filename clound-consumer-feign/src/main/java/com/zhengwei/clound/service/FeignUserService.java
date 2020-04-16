package com.zhengwei.clound.service;

import com.zhengwei.clound.service.impl.FeignUserServiceHystrix;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * TODO
 *
 * @author zevin aibaokeji
 * @version 1.0
 * 2020/4/1111:38
 **/
@FeignClient(value = "user-server",fallback = FeignUserServiceHystrix.class)
public interface FeignUserService {

    @GetMapping("user")
    public String user(@RequestParam(value = "name") String name);
}
