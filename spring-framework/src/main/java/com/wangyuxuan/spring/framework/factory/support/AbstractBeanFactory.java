package com.wangyuxuan.spring.framework.factory.support;

import com.wangyuxuan.spring.framework.factory.BeanFactory;
import com.wangyuxuan.spring.framework.ioc.BeanDefinition;
import com.wangyuxuan.spring.framework.registry.DefaultSingletonBeanRegistry;

/**
 * @author wangyuxuan
 * @date 2020/5/18 12:01
 * @description 该抽象类，定义了获取bean实例的统一步骤
 */
public abstract class AbstractBeanFactory extends DefaultSingletonBeanRegistry implements BeanFactory {

    @Override
    public Object getBean(String name) {
        // 先根据beanName去singletonObjects查询
        Object bean = getSingleton(name);
        // 如果有结果，则直接返回该实例
        if (bean != null) {
            return bean;
        }
        // 如果没有结果，则需要创建对应的Bean实例
        // 先根据beanName去beanDefinitions获取对应的BeanDefinition（封装了该Bean创建需要的相关信息）
        BeanDefinition bd = getBeanDefinition(name);
        if (bd == null) {
            return null;
        }
        // 根据BeanDefinition中封装的信息来判断要创建的Bean是单例方式创建，还是多例方式创建
        // 如果是单例方式创建实例，则需要将创建后的Bean存储到singletonObjects
        if (bd.isSingleton()) {
            bean = createBean(bd);
            addSingleton(name, bean);
        } else if (bd.isPrototype()) {
            // 如果是多例方式创建实例，则不需要将创建后的Bean存储到singletonObjects
            bean = createBean(bd);
        }
        return bean;
    }

    /**
     * 获取BeanDefinition的方式是各有不同的，所以交给子类去实现
     *
     * @param name
     * @return
     */
    public abstract BeanDefinition getBeanDefinition(String name);

    /**
     * 创建对象的方法是各有不同的，所以交给子类去实现
     *
     * @param bd
     * @return
     */
    public abstract Object createBean(BeanDefinition bd);
}
