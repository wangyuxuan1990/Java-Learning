package com.wangyuxuan.spring.framework.registry;

/**
 * @author wangyuxuan
 * @date 2020/5/18 11:44
 * @description 提供对单例对象集合的操作功能
 */
public interface SingletonBeanRegistry {

    Object getSingleton(String name);

    void addSingleton(String name, Object bean);
}
