package com.wangyuxuan.spring.demo1.service;

import com.wangyuxuan.spring.demo1.dao.UserDao;
import com.wangyuxuan.spring.demo1.po.User;

import java.util.List;
import java.util.Map;

/**
 * @author wangyuxuan
 * @date 2020/5/15 10:19
 * @description UserService实现类
 */
public class UserServiceImpl implements UserService {
    // 依赖注入UserDao
    private UserDao userDao;

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public List<User> queryUsers(Map<String, Object> param) {
        return userDao.queryUserList(param);
    }
}
