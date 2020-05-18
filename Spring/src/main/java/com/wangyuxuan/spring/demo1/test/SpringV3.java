package com.wangyuxuan.spring.demo1.test;

import com.wangyuxuan.spring.demo1.po.User;
import com.wangyuxuan.spring.demo1.service.UserService;
import com.wangyuxuan.spring.framework.factory.support.DefaultListableBeanFactory;
import com.wangyuxuan.spring.framework.reader.XmlBeanDefinitionReader;
import com.wangyuxuan.spring.framework.resource.Resource;
import com.wangyuxuan.spring.framework.resource.support.ClasspathResource;
import org.junit.Test;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wangyuxuan
 * @date 2020/5/18 9:54
 * @description 以面向对象的思维去实现IoC的相应功能 IoC思想：调用者，只需要负责bean的使用，不负责bean的创建
 */
public class SpringV3 {

    @Test
    public void test() {
        // XML解析，解析的结果，放入beanDefinitions中
        String location = "beans.xml";
        // 获取流对象
        Resource resource = new ClasspathResource(location);
        InputStream inputStream = resource.getResource();
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(beanFactory);
        // 按照spring定义的标签语义去解析Document文档
        beanDefinitionReader.registerBeanDefinitions(inputStream);
        UserService userService = (UserService) beanFactory.getBean("userService");
        // 查询参数
        Map<String, Object> param = new HashMap<>();
        param.put("username", "wangyuxuan");
        // 用户查询
        List<User> users = userService.queryUsers(param);
        System.out.println(users);
    }
}
