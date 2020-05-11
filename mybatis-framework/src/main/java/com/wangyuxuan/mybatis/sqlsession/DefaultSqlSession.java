package com.wangyuxuan.mybatis.sqlsession;

import com.wangyuxuan.mybatis.config.Configuration;

import java.util.List;

/**
 * @author wangyuxuan
 * @date 2020/5/11 9:36
 * @description 默认的SqlSession
 */
public class DefaultSqlSession implements SqlSession {

    private Configuration configuration;

    public DefaultSqlSession(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public <T> T selectOne(String statementId, Object param) {
        return null;
    }

    @Override
    public <T> List<T> selectList(String statementId, Object param) {
        return null;
    }
}
