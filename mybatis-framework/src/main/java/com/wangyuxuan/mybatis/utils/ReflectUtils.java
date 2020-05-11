package com.wangyuxuan.mybatis.utils;

/**
 * @author wangyuxuan
 * @date 2020/5/11 11:27
 * @description 反射工具类
 */
public class ReflectUtils {

    public static Class<?> resolveType(String parameterType) {
        try {
            Class<?> clazz = Class.forName(parameterType);
            return clazz;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
