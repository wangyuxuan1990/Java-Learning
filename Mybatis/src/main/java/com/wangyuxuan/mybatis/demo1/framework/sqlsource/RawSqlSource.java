package com.wangyuxuan.mybatis.demo1.framework.sqlsource;

import com.wangyuxuan.mybatis.demo1.framework.sqlnode.MixedSqlNode;
import com.wangyuxuan.mybatis.demo1.framework.sqlnode.support.DynamicContext;
import com.wangyuxuan.mybatis.demo1.framework.sqlsource.iface.SqlSource;
import com.wangyuxuan.mybatis.demo1.framework.utils.GenericTokenParser;
import com.wangyuxuan.mybatis.demo1.framework.utils.ParameterMappingTokenHandler;

/**
 * @author wangyuxuan
 * @date 2020/5/8 10:31
 * @description 封装不带有${}和动态标签的整个SQL信息 处理之前的SQL语句：SELECT * FROM user WHERE id = #{id}
 *              处理之后的SQL语句：SELECT * FROM user WHERE id = ? ---- PreparedStatement
 *              是否需要每次执行的时候，都要处理一次#{}？------不是的
 */
public class RawSqlSource implements SqlSource {

    private StaticSqlSource sqlSource;

    public RawSqlSource(MixedSqlNode rootSqlNode) {
        DynamicContext context = new DynamicContext(null);
        // 在此处解析 SqlNode集合，合并成一条SQL语句（可能还带有#{}，但是已经处理了${}和动态标签）
        rootSqlNode.apply(context);
        // 针对#{}进行处理
        // 主要来处理#{}中的参数名称，从入参对象中获取对应的参数值
        ParameterMappingTokenHandler handler = new ParameterMappingTokenHandler();
        // 根据#{ 和 }去截取字符串，最终匹配到的#{}中的内容，交给TokenHandler去处理
        GenericTokenParser tokenParser = new GenericTokenParser("#{", "}", handler);
        // 执行解析过程，返回值是处理完#{}之后的值
        String sql = tokenParser.parse(context.getSql());
        sqlSource =  new StaticSqlSource(sql, handler.getParameterMappings());
    }

    @Override
    public BoundSql getBoundSql(Object param) {
        return sqlSource.getBoundSql(param);
    }
}
