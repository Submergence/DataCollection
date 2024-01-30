//package com.DataCollection.Test;
//
//import com.DataCollection.Pojo.User;
//import com.DataCollection.Service.Impl.UserServiceImpl;
//import com.DataCollection.Service.UserService;
//
//import static org.junit.Assert.*;
//
//public class UserServiceImplTest {
//
//    UserService userService = new UserServiceImpl();
//    @org.junit.Test
//    public void registerUser() {
//        userService.registerUser(new User(null, "gyf123", "fcschalke04","123@abc.com"));
//    }
//
//    @org.junit.Test
//    public void login() {
//        System.out.println(userService.login(new User(null, "gyf123","fcschalke04",null)));
//    }
//
//    @org.junit.Test
//    public void existsUsername() {
//        if(userService.existsUsername("gyf123")){
//            System.out.println("Username has exists!");
//        }else{
//            System.out.println("Username is not exists.");
//        }
//    }
//}