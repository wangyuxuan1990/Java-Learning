package com.wangyuxuan.mybatis.sqlsource;



import com.wangyuxuan.mybatis.sqlsource.iface.SqlSource;

import java.util.List;

/**
 * @author wangyuxuan
 * @date 2020/5/8 10:31
 * @description 封装不带有${}、#{}和动态标签的整个SQL信息
 */
public class StaticSqlSource implements SqlSource {

    private String sql;

    private List<ParameterMapping> parameterMappings;

    public StaticSqlSource(String sql, List<ParameterMapping> parameterMappings) {
        this.sql = sql;
        this.parameterMappings = parameterMappings;
    }

    @Override
    public BoundSql getBoundSql(Object param) {
        return new BoundSql(sql, parameterMappings);
    }
}
