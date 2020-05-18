package com.wangyuxuan.spring.framework.factory;

import java.util.List;

/**
 * @author wangyuxuan
 * @date 2020/5/18 11:18
 * @description 可列表化的展示Spring容器中的bean实例
 */
public interface ListableBeanFactory extends BeanFactory {

    /**
     * 可以根据参数获取它和它子类型的实例，比如传递Object.class，则说明获取的是所有的实例对象
     *
     * @param clazz
     * @return
     */
    List<Object> getBeansByType(Class<?> clazz);

    /**
     * 可以根据参数获取它和它子类型的实例名称，比如传递Object.class，则说明获取的是所有的实例对象的名称
     *
     * @param clazz
     * @return
     */
    List<Object> getBeanNamesByType(Class<?> clazz);
}
