package com.wangyuxuan.spring.demo1.ioc;

/**
 * @author wangyuxuan
 * @date 2020/5/15 15:17
 * @description 封装<bean>标签中子标签<property>的value属性值
 */
public class TypedStringValue {

    // value属性值
    private String value;
    // value属性值对应的真正类型（Bean中属性的类型）
    private Class<?> targetType;

    public TypedStringValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Class<?> getTargetType() {
        return targetType;
    }

    public void setTargetType(Class<?> targetType) {
        this.targetType = targetType;
    }
}
