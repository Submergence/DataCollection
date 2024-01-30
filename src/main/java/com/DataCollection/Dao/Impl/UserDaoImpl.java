package com.DataCollection.Dao.Impl;

import com.DataCollection.Dao.UserDao;
import com.DataCollection.Pojo.User;
import com.DataCollection.Utils.MysqlConfigReader;

public class UserDaoImpl extends BaseDao implements UserDao {
    MysqlConfigReader mysqlConfigReader;

    public UserDaoImpl(){
        mysqlConfigReader = new MysqlConfigReader();
    }
    @Override
    public User queryUserByUsername(String username) {
        String sql = "SELECT `id`, `password`, `username`, `email` FROM " + mysqlConfigReader.getTable() + " WHERE username = ?";
        return QueryForOne(User.class,sql,username);
    }

    @Override
    public User queryUserByUsernameAndPassword(String username, String password) {
        String sql = "SELECT `id`, `password`, `username`, `email` FROM " + mysqlConfigReader.getTable() + " WHERE username = ? AND password = ?";
        return QueryForOne(User.class,sql,username,password);
    }

    @Override
    public int saveUser(User user) {
        String sql = "INSERT INTO " + mysqlConfigReader.getTable() + "(`username`, `password`, `email`) VALUES(?, ?, ?)";
        return update(sql, user.getUsername(), user.getPassword(), user.getEmail());
    }


}
