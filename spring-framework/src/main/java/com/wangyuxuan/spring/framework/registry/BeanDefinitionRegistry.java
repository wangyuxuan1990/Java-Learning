package com.wangyuxuan.spring.framework.registry;

import com.wangyuxuan.spring.framework.ioc.BeanDefinition;

import java.util.List;

/**
 * @author wangyuxuan
 * @date 2020/5/18 10:40
 * @description 提供对BeanDefinition集合数据的操作功能
 */
public interface BeanDefinitionRegistry {

    BeanDefinition getBeanDefinition(String name);

    void registerBeanDefinition(String name, BeanDefinition bd);

    List<BeanDefinition> getBeanDefinitions();
}
