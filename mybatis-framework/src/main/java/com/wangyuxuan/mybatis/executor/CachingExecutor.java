package com.wangyuxuan.mybatis.executor;

import com.wangyuxuan.mybatis.config.Configuration;
import com.wangyuxuan.mybatis.executor.iface.Executor;

import java.util.List;

/**
 * @author wangyuxuan
 * @date 2020/5/11 13:37
 * @description 处理二级缓存--委托模式
 */
public class CachingExecutor implements Executor {

    private Executor delegate;

    public CachingExecutor(Executor delegate) {
        this.delegate = delegate;
    }

    @Override
    public List<Object> query(String statementId, Object param, Configuration configuration) {
        // TODO 二级缓存
        // 当二级缓存没有找到对应的数据的话
        return delegate.query(statementId, param, configuration);
    }
}
