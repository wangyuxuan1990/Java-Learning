package com.wangyuxuan.mybatis.demo1.test;

import com.wangyuxuan.mybatis.builder.SqlSessionFactoryBuilder;
import com.wangyuxuan.mybatis.demo1.po.User;
import com.wangyuxuan.mybatis.factory.SqlSessionFactory;
import com.wangyuxuan.mybatis.io.Resources;
import com.wangyuxuan.mybatis.sqlsession.SqlSession;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wangyuxuan
 * @date 2020/5/10 9:30 下午
 * @description 1.以面向对象的思维去改造mybatis手写框架 2.将手写的mybatis的代码封装一个通用的框架（java工程）给程序员使用
 */
public class MybatisV3 {

    private SqlSessionFactory sqlSessionFactory;

    @Before
    public void before() {
        String location = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(location);
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
    }

    @Test
    public void test() {
        Map<String, Object> param = new HashMap<>();
        param.put("username", "wangyuxuan");
        param.put("id", 1);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        List<User> users = sqlSession.selectList("test.findUserById", param);
        System.out.println(users);
    }
}
