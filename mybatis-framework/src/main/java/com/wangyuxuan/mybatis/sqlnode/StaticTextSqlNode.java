package com.wangyuxuan.mybatis.sqlnode;


import com.wangyuxuan.mybatis.sqlnode.iface.SqlNode;

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
