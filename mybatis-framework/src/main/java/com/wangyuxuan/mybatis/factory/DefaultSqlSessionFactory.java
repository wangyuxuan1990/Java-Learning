package com.wangyuxuan.mybatis.factory;

import com.wangyuxuan.mybatis.config.Configuration;
import com.wangyuxuan.mybatis.sqlsession.DefaultSqlSession;
import com.wangyuxuan.mybatis.sqlsession.SqlSession;

/**
 * @author wangyuxuan
 * @date 2020/5/11 10:13
 * @description 默认的SqlSessionFactory
 */
public class DefaultSqlSessionFactory implements SqlSessionFactory {

    private Configuration configuration;

    public DefaultSqlSessionFactory(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public SqlSession openSession() {
        return new DefaultSqlSession(configuration);
    }
}
