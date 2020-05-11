package com.wangyuxuan.mybatis.executor;

import com.wangyuxuan.mybatis.config.Configuration;
import com.wangyuxuan.mybatis.config.MappedStatement;
import com.wangyuxuan.mybatis.handler.iface.StatementHandler;
import com.wangyuxuan.mybatis.sqlsource.BoundSql;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

/**
 * @author wangyuxuan
 * @date 2020/5/11 13:56
 * @description 默认的Executor
 */
public class SimpleExecutor extends BaseExecutor {

    @Override
    public List<Object> queryFromDataBase(String statementId, Object param, Configuration configuration, BoundSql boundSql) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = getConnection(configuration.getDataSource());
            // 先要获取MappedStatement
            MappedStatement mappedStatement = configuration.getMappedStatementById(statementId);
            // 通过BoundSql获取到SQL语句
            String sql = boundSql.getSql();
            String statementType = mappedStatement.getStatementType();
            StatementHandler statementHandler = configuration.newStatementHandler(statementType);
            // 获取statement
            Statement statement = statementHandler.prepare(connection, sql);
            // 参数处理
            statementHandler.parameterize(statement, param, boundSql);
            // 向数据库发出 sql 执行查询，查询出结果集
            List<Object> results = statementHandler.query(statement, mappedStatement);
            return results;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 释放资源
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            // 释放资源
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            // 释放资源
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }

    private Connection getConnection(DataSource dataSource) {
        try {
            Connection connection = dataSource.getConnection();
            return connection;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
