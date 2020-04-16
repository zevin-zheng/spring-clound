package com.zhengwei.clound.config;

import com.zhengwei.clound.filter.MethodFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * TODO
 *
 * @author zevin aibaokeji
 * @version 1.0
 * 2020/4/1113:55
 **/
@Configuration
public class AppConfig {

    @Bean
    public MethodFilter ipFilter() {
        return new MethodFilter();
    }
}
