package com.wangyuxuan.spring.framework.factory.support;

import com.wangyuxuan.spring.framework.ioc.BeanDefinition;
import com.wangyuxuan.spring.framework.ioc.PropertyValue;
import com.wangyuxuan.spring.framework.ioc.RuntimeBeanReference;
import com.wangyuxuan.spring.framework.ioc.TypedStringValue;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @author wangyuxuan
 * @date 2020/5/18 13:24
 * @description 该类提供了创建Bean和依赖注入Bean的功能
 */
public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory {

    @Override
    public Object createBean(BeanDefinition bd) {
        Class<?> clazzType = bd.getClazzType();
        if (clazzType == null) {
            return null;
        }
        // 第一步：Bean的实例化（new对象）
        Object bean = createBeanInstance(clazzType);
        // 第二步：Bean的属性填充（依赖注入）
        populateBean(bean, bd);
        // 第三步：Bean的初始化
        initializeBean(bean, bd);
        return bean;
    }

    /**
     * 一般该方法中会处理init-method标签和InitializingBean接口的初始化工作 还会处理Aware接口
     *
     * @param bean
     * @param bd
     */
    private void initializeBean(Object bean, BeanDefinition bd) {
        // TODO 处理Aware接口（标记）

        // TODO 处理InitializingBean的初始化操作

        // 处理初始化方法
        invokeInitMethod(bean, bd);
    }

    private void invokeInitMethod(Object bean, BeanDefinition bd) {
        try {
            String initMethod = bd.getInitMethod();
            if (initMethod == null || "".equals(initMethod)) {
                return;
            }
            Class<?> clazzType = bd.getClazzType();
            Method method = clazzType.getMethod(initMethod);
            method.invoke(bean);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void populateBean(Object bean, BeanDefinition bd) {
        List<PropertyValue> propertyValues = bd.getPropertyValues();
        for (PropertyValue pv : propertyValues) {
            String name = pv.getName();
            Object value = pv.getValue();
            // 处理参数
            Object valueToUse = resolveValue(value);
            // 属性填充
            setProperty(bean, name, valueToUse, bd);
        }
    }

    private void setProperty(Object bean, String name, Object valueToUse, BeanDefinition bd) {
        try {
            Class<?> clazzType = bd.getClazzType();
            Field field = clazzType.getDeclaredField(name);
            field.setAccessible(true);
            field.set(bean, valueToUse);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Object resolveValue(Object value) {
        if (value instanceof TypedStringValue) {
            TypedStringValue tsv = (TypedStringValue) value;
            String stringValue = tsv.getValue();
            Class<?> targetType = tsv.getTargetType();
            // TODO 可以优化
            if (targetType == Integer.class) {
                return Integer.parseInt(stringValue);
            } else if (targetType == String.class) {
                return stringValue;
            }
        } else if (value instanceof RuntimeBeanReference) {
            RuntimeBeanReference rbr = (RuntimeBeanReference) value;
            String ref = rbr.getRef();
            return getBean(ref);
        }
        return null;
    }

    /**
     * 默认使用的是无参构造
     *
     * @param clazzType
     * @return
     */
    private Object createBeanInstance(Class<?> clazzType) {
        try {
            Constructor<?> constructor = clazzType.getConstructor();
            return constructor.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
