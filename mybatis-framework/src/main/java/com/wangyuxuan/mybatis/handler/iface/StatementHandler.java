package com.wangyuxuan.mybatis.handler.iface;

import com.wangyuxuan.mybatis.config.MappedStatement;
import com.wangyuxuan.mybatis.sqlsource.BoundSql;

import java.sql.Connection;
import java.sql.Statement;
import java.util.List;

/**
 * @author wangyuxuan
 * @date 2020/5/11 14:14
 * @description 去处理关于Statement相对的逻辑
 */
public interface StatementHandler {

    /**
     * 创建Statement
     *
     * @param connection
     * @param sql
     * @return
     */
    Statement prepare(Connection connection, String sql) throws Exception;

    /**
     * 设置参数
     *
     * @param statement
     * @param param
     * @param boundSql
     */
    void parameterize(Statement statement, Object param, BoundSql boundSql) throws Exception;

    /**
     * 查询操作
     *
     * @param statement
     * @param mappedStatement
     * @return
     */
    List<Object> query(Statement statement, MappedStatement mappedStatement) throws Exception;
}
