package com.wangyuxuan.mybatis.sqlsource;


import com.wangyuxuan.mybatis.parser.SqlSourceParser;
import com.wangyuxuan.mybatis.sqlnode.DynamicContext;
import com.wangyuxuan.mybatis.sqlnode.MixedSqlNode;
import com.wangyuxuan.mybatis.sqlsource.iface.SqlSource;

/**
 * @author wangyuxuan
 * @date 2020/5/8 10:31
 * @description 封装带有${}或者动态标签的整个SQL信息 处理之前的SQL语句：SELECT * FROM user WHERE id = ${id}
 *              处理之后的SQL语句：SELECT * FROM user WHERE id = 1 ---- Statement
 *              是否需要每次执行的时候，都要处理一次${}？------是的
 */
public class DynamicSqlSource implements SqlSource {

    private MixedSqlNode rootSqlNode;

    public DynamicSqlSource(MixedSqlNode rootSqlNode) {
        this.rootSqlNode = rootSqlNode;
    }

    @Override
    public BoundSql getBoundSql(Object param) {
        DynamicContext context = new DynamicContext(param);
        // 在此处解析 SqlNode集合，合并成一条SQL语句（可能还带有#{}，但是已经处理了${}和动态标签）
        rootSqlNode.apply(context);
        System.out.println("解析#{}之前的SQL语句：" + context.getSql());
        // 针对#{}进行处理
        SqlSourceParser sqlSourceParser = new SqlSourceParser();
        SqlSource sqlSource = sqlSourceParser.parse(context.getSql());
        return sqlSource.getBoundSql(param);
    }
}
