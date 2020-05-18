package com.wangyuxuan.spring.framework.resource.support;

import com.wangyuxuan.spring.framework.resource.Resource;

import java.io.InputStream;

/**
 * @author wangyuxuan
 * @date 2020/5/18 10:28
 * @description 专门用来解决classpath目录下资源的类
 *              一个ClasspathResource就是一个资源，接口是对外提供该资源的访问功能。
 */
public class ClasspathResource implements Resource {
    // 资源路径
    private String resource;

    public ClasspathResource(String resource) {
        this.resource = resource;
    }

    @Override
    public InputStream getResource() {
        return this.getClass().getClassLoader().getResourceAsStream(resource);
    }
}
