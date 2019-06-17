package com.zhengwei.clound.controller;

import com.zhengwei.clound.bean.User;
import com.zhengwei.clound.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO
 *
 * @author zevin aibaokeji
 * @version 1.0
 * 2019/6/1612:07
 **/
@RestController
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/createIndex")
    public void createUserIndex(){
        userService.createUserIndex();
    }

    @GetMapping("/addUser")
    public void addUser(){
        User user = new User();
        user.setUserId(1L);
        user.setUserName("李晨");
        user.setAge(1);
        user.setAddress("北京");
        user.setPhone(13810746681L);
        userService.addUser(user);
    }

    @GetMapping("/getAllUser")
    public String getAllUser(){
        return userService.getAllUser();
    }

    @GetMapping("/getUser")
    public String getUser(Long userId){
        return userService.getUser(userId);
    }

    @GetMapping("/searchUserByUserName")
    public String searchUserByUserName(String userName){
        return userService.searchUserByUserName(userName);
    }

    @GetMapping("/searchUserByPhone")
    public String searchUserByPhone(String phone){
         return userService.searchUserByPhone(phone);
    }

    @GetMapping("/searchUserByAdress")
    public String searchUserByAdress(String adress){
        return userService.searchUserByAdress(adress);
    }

    @GetMapping("/batchAddUser")
    public String batchAddUser(){
        User user = new User();
        user.setUserId(1L);
        user.setUserName("郑伟和刘德华和张翠山");
        user.setAge(1);
        user.setAddress("北京市石景山区老山西里36号楼");
        user.setPhone(15844552341L);
        User user1 = new User();
        user1.setUserId(2L);
        user1.setUserName("张三和李宁和刘德华");
        user1.setAge(2);
        user1.setAddress("黑龙江省香坊区三合园小区3单元");
        user1.setPhone(15844552341L);
        User user2 = new User();
        user2.setUserId(3L);
        user2.setUserName("李宁和刘德华");
        user2.setAge(2);
        user2.setAddress("北京市昌平区永乐东里36号楼");
        user2.setPhone(15844552341L);
        User user3 = new User();
        user3.setUserId(9L);
        user3.setUserName("韩伟和李宁");
        user3.setAge(2);
        user3.setAddress("吉林省香坊区三合园");
        user3.setPhone(15844552351L);
        List<User> userList = new ArrayList<>();
        userList.add(user);
        userList.add(user1);
        userList.add(user2);
        userList.add(user3);
        return userService.batchAddUser(userList);
    }

    /**
     * 批量删除
     * @author zhengwei
     * @date 2019/6/16 14:51
     */
    @GetMapping("/batchDeleteUser")
    public String batchDeleteUser(){
        User user = new User();
        user.setUserId(1L);
        user.setUserName("郑伟");
        user.setAge(1);
        user.setAddress("老山东里");
        user.setPhone(15844552341L);
        User user1 = new User();
        user1.setUserId(2L);
        user1.setUserName("张三");
        user1.setAge(2);
        user1.setAddress("畅春园");
        user1.setPhone(15844552341L);
        User user2 = new User();
        user2.setUserId(3L);
        user2.setUserName("张四");
        user2.setAge(2);
        user2.setAddress("老山西里");
        user2.setPhone(15844552341L);
        List<User> userList = new ArrayList<>();
        userList.add(user);
        userList.add(user1);
        userList.add(user2);
        return userService.batchDeleteUser(userList);
    };

    /**
     * 批量更新
     * @author zhengwei
     * @date 2019/6/16 14:51
     */
    @GetMapping("/batchUpdateUser")
    public String batchUpdateUser(){
        User user = new User();
        user.setUserId(1L);
        user.setUserName("郑伟1");
        user.setAge(1);
        user.setAddress("老山东里");
        user.setPhone(15844552341L);
        User user1 = new User();
        user1.setUserId(2L);
        user1.setUserName("张三1");
        user1.setAge(2);
        user1.setAddress("畅春园2");
        user1.setPhone(15844552341L);
        User user2 = new User();
        user2.setUserId(3L);
        user2.setUserName("张四1");
        user2.setAge(2);
        user2.setAddress("老山西里2");
        user2.setPhone(15844552341L);
        User user3 = new User();
        user3.setUserId(9L);
        user3.setUserName("韩伟1");
        user3.setAge(2);
        user3.setAddress("崂山2");
        user3.setPhone(15844552351L);
        List<User> userList = new ArrayList<>();
        userList.add(user);
        userList.add(user1);
        userList.add(user2);
        userList.add(user3);
        return userService.batchUpdateUser(userList);
    };

    /**
     * 更新用户
     * @author zhengwei
     * @date 2019/6/16 18:46
     */
    @GetMapping("/updateUser")
    public String updateUser(){
        User user = new User();
        user.setUserId(1L);
        user.setUserName("郑伟2");
        user.setAge(1);
        user.setAddress("老山东里");
        user.setPhone(15844552341L);
        return userService.updateUser(user);
    };

    /**
     * 删除用户
     * @author zhengwei
     * @date 2019/6/16 18:47
     */
    @GetMapping("/deleteUser")
    public String deleteUser(Long userId){
        return userService.deleteUser(1L);
    };

    @GetMapping("/mohuSerachUserName")
    public String mohuSerachUserName(String userName){
        return userService.mohuSerachUserName(userName);
    }

    @GetMapping("/mohuSerachAdress")
    public String mohuSerachAdress(String adress){
        return userService.mohuSerachAdress(adress);
    }

    @GetMapping("/deleteIndex")
    public Boolean deleteIndex(){
        return userService.deleteIndex();
    }


}
