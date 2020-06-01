package com.wangyuxuan.spring.framework.aware;

import com.wangyuxuan.spring.framework.factory.BeanFactory;

/**
 * @author wangyuxuan
 * @date 2020/6/1 9:04 下午
 * @description 可以给实现了该接口的类，依赖注入一个BeanFactory的实例
 */
public interface BeanFactoryAware extends Aware {

    void setBeanFactory(BeanFactory beanFactory);
}
