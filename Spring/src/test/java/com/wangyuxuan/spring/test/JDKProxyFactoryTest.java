package com.wangyuxuan.spring.test;

import com.wangyuxuan.spring.demo2.JDKProxyFactory;
import com.wangyuxuan.spring.demo2.service.UserService;
import com.wangyuxuan.spring.demo2.service.UserServiceImpl;
import org.junit.Test;

/**
 * @author wangyuxuan
 * @date 2020/5/26 9:36
 * @description JDKProxyTest
 */
public class JDKProxyFactoryTest {

    @Test
    public void test() {
        JDKProxyFactory proxyFactory = new JDKProxyFactory(new UserServiceImpl());
        UserService proxy = (UserService) proxyFactory.getProxy();
        proxy.saveUser();
    }
}
