package com.wangyuxuan.spring.demo2;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author wangyuxuan
 * @date 2020/5/22 10:47
 * @description CGLib动态代理(ASM) 是通过子类继承父类的方式去实现的动态代理，不需要接口。
 */
public class CgLibProxyFactory implements MethodInterceptor {

    public Object getProxyByCgLib(Class clazz) {
        // 创建增强器
        Enhancer enhancer = new Enhancer();
        // 设置需要增强的类的类对象
        enhancer.setSuperclass(clazz);
        // 设置回调函数
        enhancer.setCallback(this);
        // 获取增强之后的代理对象
        return enhancer.create();
    }

    /**
     * @param proxy       这是代理对象，也就是[目标对象]的子类
     * @param method      [目标对象]的方法
     * @param args        参数
     * @param methodProxy 代理对象的方法
     * @return
     * @throws Throwable
     */
    @Override
    public Object intercept(Object proxy, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        // 因为代理对象是目标对象的子类
        // 该行代码，实际调用的是父类目标对象的方法
        System.out.println("这是cglib的代理方法");
        // 通过调用子类[代理类]的invokeSuper方法，去实际调用[目标对象]的方法
        Object returnValue = methodProxy.invokeSuper(proxy, args);
        // 代理对象调用代理对象的invokeSuper方法，而invokeSuper方法会去调用目标类的 invoke方法完成目标对象的调用
        return returnValue;
    }
}
