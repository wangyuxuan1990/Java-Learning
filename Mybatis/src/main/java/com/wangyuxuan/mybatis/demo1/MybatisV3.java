package com.wangyuxuan.mybatis.demo1;

import com.wangyuxuan.mybatis.demo1.po.User;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wangyuxuan
 * @date 2020/5/10 9:30 下午
 * @description 1.以面向对象的思维去改造mybatis手写框架 2.将手写的mybatis的代码封装一个通用的框架（java工程）给程序员使用
 */
public class MybatisV3 {
    @Test
    public void test() {
        loadConfiguration();
        Map<String, Object> param = new HashMap<>();
        param.put("username", "wangyuxuan");
        param.put("id", 1);
        List<User> users = SqlSession.selectList("test.findUserById", param);
        System.out.println(users);
    }
}
