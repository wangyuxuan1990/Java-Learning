package com.wangyuxuan.mybatis.sqlsession;

import com.wangyuxuan.mybatis.config.Configuration;
import com.wangyuxuan.mybatis.executor.iface.Executor;

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
        List<Object> list = selectList(statementId, param);
        if (list != null && list.size() == 1) {
            return (T) list.get(0);
        }
        return null;
    }

    @Override
    public <T> List<T> selectList(String statementId, Object param) {
        // CachingExecutor--委托模式-->BaseExecutor--抽象模板方法模式-->SimpleExecutor
        Executor executor = configuration.newExecutor();
        return (List<T>) executor.query(statementId, param, configuration);
    }
}
