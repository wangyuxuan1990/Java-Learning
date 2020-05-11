package com.wangyuxuan.mybatis.executor;

import com.wangyuxuan.mybatis.config.Configuration;
import com.wangyuxuan.mybatis.config.MappedStatement;
import com.wangyuxuan.mybatis.executor.iface.Executor;
import com.wangyuxuan.mybatis.sqlsource.BoundSql;
import com.wangyuxuan.mybatis.sqlsource.iface.SqlSource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wangyuxuan
 * @date 2020/5/11 13:45
 * @description 主要是用来处理一级缓存--抽象模板方法模式
 */
public abstract class BaseExecutor implements Executor {

    private Map<String, List<Object>> oneLevelCache = new HashMap<>();

    @Override
    public List<Object> query(String statementId, Object param, Configuration configuration) {
        MappedStatement mappedStatement = configuration.getMappedStatementById(statementId);
        SqlSource sqlSource = mappedStatement.getSqlSource();
        BoundSql boundSql = sqlSource.getBoundSql(param);
        // 从一级缓存中查找对应的结果
        List<Object> list = oneLevelCache.get(boundSql.getSql());
        // 如果有，则直接返回
        if (list != null && list.size() > 0) {
            return list;
        }
        // 如果没有，则走对应的实现类Executor
        list = queryFromDataBase(statementId, param, configuration, boundSql);
        oneLevelCache.put(boundSql.getSql(), list);
        return list;
    }

    public abstract List<Object> queryFromDataBase(String statementId, Object param, Configuration configuration, BoundSql boundSql);
}
