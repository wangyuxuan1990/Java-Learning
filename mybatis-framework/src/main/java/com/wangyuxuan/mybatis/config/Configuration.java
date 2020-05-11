package com.wangyuxuan.mybatis.config;

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
}
