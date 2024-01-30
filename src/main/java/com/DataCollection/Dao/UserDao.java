package com.DataCollection.Dao;

import com.DataCollection.Pojo.User;

public interface UserDao {
    /**
     * 根据用户名查询用户信息
     * @param username 用户名
     * @return 返回信息，返回null说明不存在用户
     */
    public User queryUserByUsername(String username);

    /**
     * 根据用户名密码查询用户信息
     * @param username
     * @param password
     * @return 同上
     */
    public User queryUserByUsernameAndPassword(String username, String password);

    /**
     * 保存用户信息
     * @param user
     * @return 影响的行数，-1为失败
     */
    public int saveUser(User user);

}
