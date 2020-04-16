package com.zhengwei.clound.service;

/**
 * ${description}
 *
 * @author zhengwei 卓望信息(北京)
 * @version 1.0
 * @since 1.0
 * 2019/5/23 16:54
 **/
public interface RibbonService {

     String hello(String name);

     String user(String name);

     String productFallBack(String name);

     String userFallBack(String name);
}
