package com.wangyuxuan.spring.framework.factory;

/**
 * @author wangyuxuan
 * @date 2020/5/18 10:37
 * @description Spring容器的顶级接口
 */
public interface BeanFactory {

    Object getBean(String name);
}
