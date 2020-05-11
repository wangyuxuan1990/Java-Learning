package com.wangyuxuan.mybatis.handler.iface;

import com.wangyuxuan.mybatis.sqlsource.BoundSql;

import java.sql.Statement;

/**
 * @author wangyuxuan
 * @date 2020/5/11 14:55
 * @description 处理Parameter逻辑
 */
public interface ParameterHandler {

    void setParameter(Statement statement, Object param, BoundSql boundSql) throws Exception;
}
