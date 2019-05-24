package com.zhengwei.clound.service.impl;

import com.zhengwei.clound.service.RibbonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * ${description}
 *
 * @author zhengwei 卓望信息(北京)
 * @version 1.0
 * @since 1.0
 * 2019/5/23 16:54
 **/
@Service
public class RibbonServiceImpl implements RibbonService {

    @Autowired
    RestTemplate restTemplate;

    @Override
    public String hello(String name) {
        return restTemplate.getForObject("http://product-server/hello?name="+name,String.class);
    }
}
