package com.DataCollection.Service.Impl;

import com.DataCollection.Dao.Impl.UserDaoImpl;
import com.DataCollection.Dao.UserDao;
import com.DataCollection.Service.UserService;
import com.DataCollection.Pojo.User;

public class UserServiceImpl implements UserService {

    private UserDao userDao = new UserDaoImpl();
    @Override
    public void registerUser(User user) {
        userDao.saveUser(user);
    }

    @Override
    public User login(User user) {
        return userDao.queryUserByUsernameAndPassword(user.getUsername(), user.getPassword());
    }

    @Override
    public boolean existsUsername(String username) {
        if(userDao.queryUserByUsername(username) == null){
            // return null means the username is available.
            return false;
        }
        return true;
    }
}
