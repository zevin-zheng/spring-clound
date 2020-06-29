package com.zhengwei.clound.service.impl;

import com.zhengwei.clound.service.FeignProductService;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 实现feign接口用于熔断处理
 *
 * @author zhengwei 卓望信息(北京)
 * @version 1.0
 * @since 1.0
 * 2019/5/24 11:34
 **/
@Component
@Slf4j
public class FeignProductServiceHystrix implements FallbackFactory<FeignProductService> {

    @Override
    public FeignProductService create(Throwable throwable) {
        log.error("FeignProductService回退：", throwable);
        return new FeignProductService() {
            @Override
            public String hello(String name) {
                return "sorry "+name+"，product has fail!";
            }
        };
    }
}
