package com.wangyuxuan.mybatis.io;

import java.io.InputStream;

/**
 * @author wangyuxuan
 * @date 2020/5/11 11:14
 * @description 读取io
 */
public class Resources {

    public static InputStream getResourceAsStream(String location) {
        InputStream inputStream = Resources.class.getClassLoader().getResourceAsStream(location);
        return inputStream;
    }
}
