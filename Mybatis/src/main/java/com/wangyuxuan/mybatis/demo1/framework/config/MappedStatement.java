package com.wangyuxuan.mybatis.demo1.framework.config;

import com.wangyuxuan.mybatis.demo1.framework.sqlsource.iface.SqlSource;

/**
 * @author wangyuxuan
 * @date 2020/5/2 3:23 下午
 * @description 封装了映射文件中select/update/delete/insert标签信息
 */
public class MappedStatement {

    private String statementId;

    private SqlSource sqlSource;

    private String statementType;

    private Class<?> parameterTypeClass;

    private Class<?> resultTypeClass;

    public MappedStatement(String statementId, SqlSource sqlSource, String statementType, Class<?> parameterTypeClass, Class<?> resultTypeClass) {
        this.statementId = statementId;
        this.sqlSource = sqlSource;
        this.statementType = statementType;
        this.parameterTypeClass = parameterTypeClass;
        this.resultTypeClass = resultTypeClass;
    }

    public String getStatementId() {
        return statementId;
    }

    public void setStatementId(String statementId) {
        this.statementId = statementId;
    }

    public SqlSource getSqlSource() {
        return sqlSource;
    }

    public void setSqlSource(SqlSource sqlSource) {
        this.sqlSource = sqlSource;
    }

    public String getStatementType() {
        return statementType;
    }

    public void setStatementType(String statementType) {
        this.statementType = statementType;
    }

    public Class<?> getParameterTypeClass() {
        return parameterTypeClass;
    }

    public void setParameterTypeClass(Class<?> parameterTypeClass) {
        this.parameterTypeClass = parameterTypeClass;
    }

    public Class<?> getResultTypeClass() {
        return resultTypeClass;
    }

    public void setResultTypeClass(Class<?> resultTypeClass) {
        this.resultTypeClass = resultTypeClass;
    }
}
