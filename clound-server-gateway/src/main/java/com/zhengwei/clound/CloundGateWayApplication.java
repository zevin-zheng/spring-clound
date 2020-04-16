package com.zhengwei.clound;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * TODO
 *
 * @author zevin aibaokeji
 * @version 1.0
 * 2020/4/1116:00
 **/
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class CloundGateWayApplication {

    public static void main(String[] args) {SpringApplication.run(CloundGateWayApplication.class, args);
    }
}
