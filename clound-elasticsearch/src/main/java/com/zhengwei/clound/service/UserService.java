package com.zhengwei.clound.service;

import com.zhengwei.clound.bean.User;

import java.io.IOException;
import java.util.List;

/**
 * TODO
 *
 * @author zevin aibaokeji
 * @version 1.0
 * 2019/6/1612:07
 **/
public interface UserService {

    /**
     * 创建用户索引
     * @return 创建是否成功
     * @author zhengwei
     * @date 2019/6/16 12:30
     */
    public boolean createUserIndex();

    /**
     * 创建一个用户
     * @param user 用户数据
     * @return 是否创建成功
     */
    public boolean addUser(User user);

    /**
     * 获取所有用户
     * @author zhengwei
     * @date 2019/6/16 13:44
     */
    public String getAllUser();

    /**
     * TODO 获取所有用户
     * @param  userId  用户Id
     * @author zhengwei
     * @date 2019/6/16 13:44
     */
    public String getUser(Long userId);

    /**
     * 模糊搜索
     * @param search 搜索参数
     * @return 结果集
     */
    public String searchUserByUserName(String search);

    /**
     * 模糊搜索
     * @param search 搜索参数
     * @return 结果集
     */
    public String searchUserByPhone(String search);

    /**
     * 模糊搜索
     * @param search 搜索参数
     * @return 结果集
     */
    public String searchUserByAdress(String search);

    /**
     * 批量创建
     * @author zhengwei
     * @date 2019/6/16 14:51
     */
    public String batchAddUser(List<User> users);

    /**
     * 批量删除
     * @author zhengwei
     * @date 2019/6/16 14:51
     */
    public String batchDeleteUser(List<User> users);

    /**
     * 批量更新
     * @author zhengwei
     * @date 2019/6/16 14:51
     */
    public String batchUpdateUser(List<User> users);

    /**
     * 判断索引是否存在
     * @author zhengwei
     * @date 2019/6/16 18:43
     */
    public boolean existsIndex() throws IOException;

    /**
     * 判断记录是否存在
     * @author zhengwei
     * @date 2019/6/16 18:43
     */
    public boolean exists(User user) throws IOException;

    /**
     * 更新用户
     * @param  user 用户
     * @author zhengwei
     * @date 2019/6/16 18:46
     */
    public String updateUser(User user);

    /**
     * 删除用户
     * @author zhengwei
     * @date 2019/6/16 18:47
     */
    public String deleteUser(Long userId);

    public String mohuSerachUserName(String search);

    public String mohuSerachAdress(String search);

    /**
     * 删除索引
     * @author zhengwei
     * @date 2019/6/16 18:47
     */
    public boolean deleteIndex();

}
