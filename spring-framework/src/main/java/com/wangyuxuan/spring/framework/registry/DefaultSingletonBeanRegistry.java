package com.wangyuxuan.spring.framework.registry;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wangyuxuan
 * @date 2020/5/18 11:46
 * @description 封装了单例bean存储的Map集合对象，并且提供接口对外提供操作功能
 */
public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {

    /**
     * 存储Bean实例（单例Bean） K：beanName V：Bean实例
     */
    private Map<String, Object> singletonObjects = new HashMap<>();

    @Override
    public Object getSingleton(String name) {
        return singletonObjects.get(name);
    }

    @Override
    public void addSingleton(String name, Object bean) {
        singletonObjects.put(name, bean);
    }
}
