package com.wangyuxuan.spring.framework.factory.support;

import com.wangyuxuan.spring.framework.factory.ListableBeanFactory;
import com.wangyuxuan.spring.framework.ioc.BeanDefinition;
import com.wangyuxuan.spring.framework.registry.BeanDefinitionRegistry;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wangyuxuan
 * @date 2020/5/18 11:30
 * @description 真正的Spring容器类
 */
public class DefaultListableBeanFactory extends AbstractAutowireCapableBeanFactory implements BeanDefinitionRegistry, ListableBeanFactory {

    /**
     * 存储Bean实例（单例Bean） K：beanName V：BeanDefinition实例
     */
    private Map<String, BeanDefinition> beanDefinitions = new HashMap<>();

    @Override
    public BeanDefinition getBeanDefinition(String name) {
        return beanDefinitions.get(name);
    }

    @Override
    public void registerBeanDefinition(String name, BeanDefinition bd) {
        beanDefinitions.put(name, bd);
    }

    @Override
    public List<BeanDefinition> getBeanDefinitions() {
        // TODO 先留着
        return null;
    }

    @Override
    public List<Object> getBeansByType(Class<?> clazz) {
        // TODO 先留着
        return null;
    }

    @Override
    public List<Object> getBeanNamesByType(Class<?> clazz) {
        // TODO 先留着
        return null;
    }
}
