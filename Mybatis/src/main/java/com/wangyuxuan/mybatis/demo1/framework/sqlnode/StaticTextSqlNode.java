package com.wangyuxuan.mybatis.demo1.framework.sqlnode;

import com.wangyuxuan.mybatis.demo1.framework.sqlnode.iface.SqlNode;
import com.wangyuxuan.mybatis.demo1.framework.sqlnode.support.DynamicContext;

/**
 * @author wangyuxuan
 * @date 2020/5/8 10:35
 * @description 封装不带有${}的SQL文本信息
 */
public class StaticTextSqlNode implements SqlNode {

    private String sqlText;

    public StaticTextSqlNode(String sqlText) {
        this.sqlText = sqlText;
    }

    @Override
    public void apply(DynamicContext context) {
        context.appendSql(sqlText);
    }
}
