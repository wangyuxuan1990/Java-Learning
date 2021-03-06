package com.wangyuxuan.mybatis.sqlsource;


import com.wangyuxuan.mybatis.parser.SqlSourceParser;
import com.wangyuxuan.mybatis.sqlnode.DynamicContext;
import com.wangyuxuan.mybatis.sqlnode.MixedSqlNode;
import com.wangyuxuan.mybatis.sqlsource.iface.SqlSource;

/**
 * @author wangyuxuan
 * @date 2020/5/8 10:31
 * @description 封装不带有${}和动态标签的整个SQL信息 处理之前的SQL语句：SELECT * FROM user WHERE id = #{id}
 *              处理之后的SQL语句：SELECT * FROM user WHERE id = ? ---- PreparedStatement
 *              是否需要每次执行的时候，都要处理一次#{}？------不是的
 */
public class RawSqlSource implements SqlSource {

    private SqlSource sqlSource;

    public RawSqlSource(MixedSqlNode rootSqlNode) {
        DynamicContext context = new DynamicContext(null);
        // 在此处解析 SqlNode集合，合并成一条SQL语句（可能还带有#{}，但是已经处理了${}和动态标签）
        rootSqlNode.apply(context);
        // 针对#{}进行处理
        SqlSourceParser sqlSourceParser = new SqlSourceParser();
        sqlSource = sqlSourceParser.parse(context.getSql());
    }

    @Override
    public BoundSql getBoundSql(Object param) {
        return sqlSource.getBoundSql(param);
    }
}
