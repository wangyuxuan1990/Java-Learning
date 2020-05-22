package com.wangyuxuan.spring.demo2.service;


public class UserServiceImpl implements UserService {

    @Override
    public void saveUser() {
        System.out.println("添加用户");
    }

    @Override
    public void saveUser(String name) {
        System.out.println("添加用户 : name");
    }

    @Override
    public void updateUser() {
        System.out.println("修改用户");
    }

}
