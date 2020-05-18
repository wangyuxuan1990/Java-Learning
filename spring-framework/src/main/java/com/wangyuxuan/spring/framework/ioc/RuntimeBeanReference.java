package com.wangyuxuan.spring.framework.ioc;

/**
 * @author wangyuxuan
 * @date 2020/5/15 15:14
 * @description 封装<bean>标签中子标签<property>的ref属性值
 */
public class RuntimeBeanReference {

    // ref的属性值
    private String ref;

    public RuntimeBeanReference(String ref) {
        this.ref = ref;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }
}
