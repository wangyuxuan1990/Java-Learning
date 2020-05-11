package com.wangyuxuan.mybatis.sqlnode;


import com.wangyuxuan.mybatis.sqlnode.iface.SqlNode;
import com.wangyuxuan.mybatis.utils.OgnlUtils;

/**
 * @author wangyuxuan
 * @date 2020/5/8 10:36
 * @description 封装if标签对应的sql脚本信息
 */
public class IfSqlNode implements SqlNode {

    // 判断条件(OGNL表达式)
    private String test;
    // if标签内的SqlNode集合信息
    private MixedSqlNode mixedSqlNode;

    public IfSqlNode(String test, MixedSqlNode mixedSqlNode) {
        this.test = test;
        this.mixedSqlNode = mixedSqlNode;
    }

    @Override
    public void apply(DynamicContext context) {
        // 获取入参对象
        Object parameterObject = context.getBindings().get("_parameter");
        // 使用Ognl工具类去判断表达式
        if (OgnlUtils.evaluateBoolean(test, parameterObject)) {
            mixedSqlNode.apply(context);
        }
    }
}
