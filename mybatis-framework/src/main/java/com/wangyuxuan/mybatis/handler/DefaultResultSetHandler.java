package com.wangyuxuan.mybatis.handler;

import com.wangyuxuan.mybatis.config.MappedStatement;
import com.wangyuxuan.mybatis.handler.iface.ResultSetHandler;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wangyuxuan
 * @date 2020/5/11 15:21
 * @description 专门用来处理结果集的处理类
 */
public class DefaultResultSetHandler implements ResultSetHandler {

    @Override
    public List<Object> handleResultSets(MappedStatement mappedStatement, ResultSet rs) throws Exception {
        List<Object> results = new ArrayList<>();
        Class<?> resultClassType = mappedStatement.getResultTypeClass();
        // 遍历输出查询结果集
        while (rs.next()) {
            Object result = resultClassType.newInstance();
            ResultSetMetaData resultSetMetaData = rs.getMetaData();
            int columnCount = resultSetMetaData.getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                String columnName = resultSetMetaData.getColumnName(i);
                Field field = resultClassType.getDeclaredField(columnName);
                field.setAccessible(true);
                field.set(result, rs.getObject(columnName));
            }
            results.add(result);
        }
        return results;
    }
}
