package com.wangyuxuan.spring.demo2;


import com.wangyuxuan.spring.demo2.service.UserService;
import com.wangyuxuan.spring.demo2.service.UserServiceImpl;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author wangyuxuan
 * @date 2020/5/22 10:44
 * @description JDK动态代理 基于接口去实现的动态代理
 *              JDK代理对象工厂&代理对象方法调用处理器
 */
public class JDKProxyFactory implements InvocationHandler {
    // 目标对象的引用
    private Object target;

    // 通过构造方法将目标对象注入到代理对象中
    public JDKProxyFactory(Object target) {
        super();
        this.target = target;
    }

    public Object getProxy() {
        // 如何生成一个代理类呢？
        // 1、编写源文件
        // 2、编译源文件为class文件
        // 3、将class文件加载到JVM中(ClassLoader)
        // 4、将class文件对应的对象进行实例化（反射）

        // Proxy是JDK中的API类
        // 第一个参数：目标对象的类加载器
        // 第二个参数：目标对象的接口
        // 第二个参数：代理对象的执行处理器
        Object object = Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), this);
        return object;
    }

    /**
     * 代理对象会执行的方法
     *
     * @param proxy
     * @param method
     * @param args
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Method method1 = target.getClass().getMethod("saveUser", null);
//        Method method2 = Class.forName("com.sun.proxy.$Proxy4").getMethod("saveUser", null);
        System.out.println("目标对象的方法：" + method1.toString());
//        System.out.println("代理对象的方法：" + method2.toString());
        System.out.println("目标对象的接口：" + method.toString());
        System.out.println("这是jdk的代理方法");
        // 下面的代码，是反射中的API用法
        // 该行代码，实际调用的是[目标对象]的方法
        // 利用反射，调用[目标对象]的方法
        Object returnValue = method.invoke(target, args);
        return returnValue;
    }

    public static void main(String[] args) {
        JDKProxyFactory proxyFactory = new JDKProxyFactory(new UserServiceImpl());
        UserService proxy = (UserService) proxyFactory.getProxy();
        proxy.saveUser();
    }
}
