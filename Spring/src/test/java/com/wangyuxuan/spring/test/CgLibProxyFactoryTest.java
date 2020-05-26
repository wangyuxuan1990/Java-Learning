package com.wangyuxuan.spring.test;

import com.wangyuxuan.spring.demo2.CgLibProxyFactory;
import com.wangyuxuan.spring.demo2.service.UserService;
import com.wangyuxuan.spring.demo2.service.UserServiceImpl;
import org.junit.Test;

/**
 * @author wangyuxuan
 * @date 2020/5/26 9:47
 * @description CgLibProxyFactoryTest
 */
public class CgLibProxyFactoryTest {

    @Test
    public void test() {
        CgLibProxyFactory proxyFactory = new CgLibProxyFactory();
        UserService proxy = (UserService) proxyFactory.getProxyByCgLib(UserServiceImpl.class);
        proxy.saveUser();
    }
}
