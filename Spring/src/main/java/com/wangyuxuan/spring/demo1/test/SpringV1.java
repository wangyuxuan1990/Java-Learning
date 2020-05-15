package com.wangyuxuan.spring.demo1.test;

import com.wangyuxuan.spring.demo1.dao.UserDaoImpl;
import com.wangyuxuan.spring.demo1.po.User;
import com.wangyuxuan.spring.demo1.service.UserServiceImpl;
import org.apache.commons.dbcp.BasicDataSource;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wangyuxuan
 * @date 2020/5/15 10:23
 * @description V1版本实现 自己负责构造对应的实例
 */
public class SpringV1 {

    @Test
    public void test() {
        UserServiceImpl userService = new UserServiceImpl();
        UserDaoImpl userDao = new UserDaoImpl();
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/mybatis");
        dataSource.setUsername("root");
        dataSource.setPassword("123456");
        userDao.setDataSource(dataSource);
        userService.setUserDao(userDao);

        // 查询参数
        Map<String, Object> param = new HashMap<>();
        param.put("username", "wangyuxuan");
        // 用户查询
        List<User> users = userService.queryUsers(param);
        System.out.println(users);
    }
}
