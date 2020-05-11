package com.wangyuxuan.mybatis.parser;

import com.wangyuxuan.mybatis.sqlsource.StaticSqlSource;
import com.wangyuxuan.mybatis.sqlsource.iface.SqlSource;
import com.wangyuxuan.mybatis.utils.GenericTokenParser;
import com.wangyuxuan.mybatis.utils.ParameterMappingTokenHandler;

/**
 * @author wangyuxuan
 * @date 2020/5/11 15:52
 * @description 主要用来解析#{}
 */
public class SqlSourceParser {

    public SqlSource parse(String sql) {
        // 主要来处理#{}中的参数名称，从入参对象中获取对应的参数值
        ParameterMappingTokenHandler handler = new ParameterMappingTokenHandler();
        // 根据#{ 和 }去截取字符串，最终匹配到的#{}中的内容，交给TokenHandler去处理
        GenericTokenParser tokenParser = new GenericTokenParser("#{", "}", handler);
        // 执行解析过程，返回值是处理完#{}之后的值
        sql = tokenParser.parse(sql);
        System.out.println("解析#{}之后的SQL语句：" + sql);
        return new StaticSqlSource(sql, handler.getParameterMappings());
    }
}
