package com.wangyuxuan.mybatis.demo1.framework.sqlnode.iface;

import com.wangyuxuan.mybatis.demo1.framework.sqlnode.support.DynamicContext;

/**
 * @author wangyuxuan
 * @date 2020/5/8 10:33
 * @description 提供对封装的SqlNode信息进行解析处理
 */
public interface SqlNode {

    /**
     * 节点处理
     *
     * @param context 节点处理时需要的上下文信息
     */
    void apply(DynamicContext context);
}
