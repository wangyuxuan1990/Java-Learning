package com.wangyuxuan.spring.demo1.ioc;

/**
 * @author wangyuxuan
 * @date 2020/5/15 14:07
 * @description PropertyValue就封装着一个property标签的信息
 */
public class PropertyValue {

    private String name;

    private Object value;

    public PropertyValue(String name, Object value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
