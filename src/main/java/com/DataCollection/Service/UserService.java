package com.DataCollection.Service;

import com.DataCollection.Pojo.User;

public interface UserService {
    /**
     * Register User
     * @param user
     */
    public void registerUser(User user);

    /**
     * Login
     * @param user
     * @return return null - fail, return values - success
     */
    public User login(User user);

    /**
     * Check if username is available
     * @param username
     * @return ture-available, false-unavailable
     */
    public  boolean existsUsername(String username);


}
