package com.wangyuxuan.spring.framework.resource;

import java.io.InputStream;

/**
 * @author wangyuxuan
 * @date 2020/5/18 10:26
 * @description 提供操作资源的功能（XML就是一种资源，这个资源可能在互联网上、可能在本地磁盘上、classpath）
 */
public interface Resource {

    InputStream getResource();
}
