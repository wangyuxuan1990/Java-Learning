package com.wangyuxuan.mybatis.demo1.framework.sqlsource;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wangyuxuan
 * @date 2020/5/2 3:38 下午
 * @description 存储经过处理之后的JDBC可以直接执行的SQL语句
 */
public class BoundSql {

    // JDBC可以执行的SQL语句
    // SELECT * FROM user WHERE username = ? AND sex = ?
    private String sql;

    // 占位符?对应的参数信息（参数名称、参数类型）
    private List<ParameterMapping> parameterMappings = new ArrayList<>();

    public BoundSql(String sql, List<ParameterMapping> parameterMappings) {
        this.sql = sql;
        this.parameterMappings = parameterMappings;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public List<ParameterMapping> getParameterMappings() {
        return parameterMappings;
    }

    public void addParameterMapping(ParameterMapping parameterMapping) {
        this.parameterMappings.add(parameterMapping);
    }
}
