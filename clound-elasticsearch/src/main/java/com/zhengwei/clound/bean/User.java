package com.zhengwei.clound.bean;

import lombok.Data;

import java.io.Serializable;

/**
 * TODO
 *
 * @author zevin aibaokeji
 * @version 1.0
 * 2019/6/1612:05
 **/
@Data
public class User implements Serializable {

    private String userName;

    private Integer age;

    private Long userId;

    private Long phone;

    private String address;

    private Long cityId;

}
