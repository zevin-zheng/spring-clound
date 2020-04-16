package com.zhengwei.clound.service.impl;

import com.zhengwei.clound.service.FeignUserService;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * TODO
 *
 * @author zevin aibaokeji
 * @version 1.0
 * 2020/4/1111:39
 **/
@Component
public class FeignUserServiceHystrix implements FeignUserService {

    @Override
    public String user(@RequestParam(value = "name") String name) {
        return "sorry "+name+"，user has fail!";
    }
}
