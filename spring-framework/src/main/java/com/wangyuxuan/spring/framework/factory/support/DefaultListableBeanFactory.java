package com.wangyuxuan.spring.framework.factory.support;

import com.wangyuxuan.spring.framework.factory.ListableBeanFactory;
import com.wangyuxuan.spring.framework.ioc.BeanDefinition;
import com.wangyuxuan.spring.framework.registry.BeanDefinitionRegistry;

import java.util.ArrayList;
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
        List<BeanDefinition> beanDefinitionList = new ArrayList<>();
        for (BeanDefinition beanDefinition : beanDefinitions.values()) {
            beanDefinitionList.add(beanDefinition);
        }
        return beanDefinitionList;
    }

    @Override
    public <T> List<T> getBeansByType(Class<?> clazz) {
        List<T> results = new ArrayList<>();
        for (BeanDefinition bd : beanDefinitions.values()) {
            String clazzName = bd.getClazzName();
            Class<?> type = resolveClassName(clazzName);
            if (clazz.isAssignableFrom(type)) {
                Object bean = getBean(bd.getBeanName());
                results.add((T) bean);
            }
        }
        return results;
    }

    private Class<?> resolveClassName(String clazzName) {
        try {
            Class<?> type = Class.forName(clazzName);
            return type;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Object> getBeanNamesByType(Class<?> clazz) {
        // TODO 先留着
        return null;
    }
}
