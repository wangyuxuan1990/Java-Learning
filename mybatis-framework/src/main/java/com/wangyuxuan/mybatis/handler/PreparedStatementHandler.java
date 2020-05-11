package com.wangyuxuan.mybatis.handler;

import com.wangyuxuan.mybatis.config.Configuration;
import com.wangyuxuan.mybatis.config.MappedStatement;
import com.wangyuxuan.mybatis.handler.iface.ParameterHandler;
import com.wangyuxuan.mybatis.handler.iface.ResultSetHandler;
import com.wangyuxuan.mybatis.handler.iface.StatementHandler;
import com.wangyuxuan.mybatis.sqlsource.BoundSql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

/**
 * @author wangyuxuan
 * @date 2020/5/11 14:22
 * @description 预编译的Statement
 */
public class PreparedStatementHandler implements StatementHandler {

    private ParameterHandler parameterHandler;

    private ResultSetHandler resultSetHandler;

    public PreparedStatementHandler(Configuration configuration) {
        this.parameterHandler = configuration.newParameterHandler();
        this.resultSetHandler = configuration.newResultSetHandler();
    }

    @Override
    public Statement prepare(Connection connection, String sql) throws Exception {
        return connection.prepareStatement(sql);
    }

    @Override
    public void parameterize(Statement statement, Object param, BoundSql boundSql) throws Exception {
        parameterHandler.setParameter(statement, param, boundSql);
    }

    @Override
    public List<Object> query(Statement statement, MappedStatement mappedStatement) throws Exception {
        PreparedStatement preparedStatement = (PreparedStatement) statement;
        ResultSet rs = preparedStatement.executeQuery();
        List<Object> results = resultSetHandler.handleResultSets(mappedStatement, rs);
        return results;
    }
}
