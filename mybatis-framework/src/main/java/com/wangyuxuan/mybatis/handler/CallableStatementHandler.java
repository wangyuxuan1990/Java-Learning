package com.wangyuxuan.mybatis.handler;

import com.wangyuxuan.mybatis.config.MappedStatement;
import com.wangyuxuan.mybatis.handler.iface.StatementHandler;
import com.wangyuxuan.mybatis.sqlsource.BoundSql;

import java.sql.Connection;
import java.sql.Statement;
import java.util.List;

/**
 * @author wangyuxuan
 * @date 2020/5/11 14:22
 * @description 存储过程的Statement
 */
public class CallableStatementHandler implements StatementHandler {

    @Override
    public Statement prepare(Connection connection, String sql) throws Exception {
        return null;
    }

    @Override
    public void parameterize(Statement statement, Object param, BoundSql boundSql) throws Exception {

    }

    @Override
    public List<Object> query(Statement statement, MappedStatement mappedStatement) throws Exception {
        return null;
    }
}
