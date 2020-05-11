package com.wangyuxuan.mybatis.executor.iface;

import com.wangyuxuan.mybatis.config.Configuration;

import java.util.List;

/**
 * @author wangyuxuan
 * @date 2020/5/11 13:27
 * @description 执行器接口，提供执行JDBC代码的功能
 */
public interface Executor {

    List<Object> query(String statementId, Object param, Configuration configuration);
}
