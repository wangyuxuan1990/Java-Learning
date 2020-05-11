package com.wangyuxuan.mybatis.config;

import com.wangyuxuan.mybatis.executor.CachingExecutor;
import com.wangyuxuan.mybatis.executor.SimpleExecutor;
import com.wangyuxuan.mybatis.executor.iface.Executor;
import com.wangyuxuan.mybatis.handler.DefaultParameterHandler;
import com.wangyuxuan.mybatis.handler.DefaultResultSetHandler;
import com.wangyuxuan.mybatis.handler.PreparedStatementHandler;
import com.wangyuxuan.mybatis.handler.iface.ParameterHandler;
import com.wangyuxuan.mybatis.handler.iface.ResultSetHandler;
import com.wangyuxuan.mybatis.handler.iface.StatementHandler;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wangyuxuan
 * @date 2020/5/2 3:15 下午
 * @description 封装了全局配置文件和映射文件
 */
public class Configuration {

    private DataSource dataSource;

    private Map<String, MappedStatement> mappedStatements = new HashMap<>();

    private boolean useCache = true;

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public MappedStatement getMappedStatementById(String statementId) {
        return mappedStatements.get(statementId);
    }

    public void addMappedStatement(String statementId, MappedStatement mappedStatement) {
        this.mappedStatements.put(statementId, mappedStatement);
    }

    public Executor newExecutor() {
        Executor executor = null;
        // 如果参数是simple
        executor = new SimpleExecutor();
        // 如果启用缓存，则Executor为CachingExecutor
        if (useCache) {
            // 对Executor进行缓存功能的装饰
            executor = new CachingExecutor(executor);
        }
        return executor;
    }

    public StatementHandler newStatementHandler(String statementType) {
        if (statementType.equals("prepared")) {
            return new PreparedStatementHandler(this);
        }
        return null;
    }

    public ParameterHandler newParameterHandler() {
        return new DefaultParameterHandler();
    }

    public ResultSetHandler newResultSetHandler() {
        return new DefaultResultSetHandler();
    }
}
