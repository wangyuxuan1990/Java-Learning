package com.wangyuxuan.mybatis.handler;

import com.wangyuxuan.mybatis.handler.iface.ParameterHandler;
import com.wangyuxuan.mybatis.sqlsource.BoundSql;
import com.wangyuxuan.mybatis.sqlsource.ParameterMapping;
import com.wangyuxuan.mybatis.utils.SimpleTypeRegistry;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

/**
 * @author wangyuxuan
 * @date 2020/5/11 15:06
 * @description 专门来处理参数设置的类
 */
public class DefaultParameterHandler implements ParameterHandler {

    @Override
    public void setParameter(Statement statement, Object param, BoundSql boundSql) throws Exception {
        PreparedStatement preparedStatement = (PreparedStatement) statement;
        Class<?> paramClassType = param.getClass();
        // 设置参数
        if (SimpleTypeRegistry.isSimpleType(paramClassType)) {
            preparedStatement.setObject(1, param);
        } else {
            List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
            for (int i = 0; i < parameterMappings.size(); i++) {
                ParameterMapping parameterMapping = parameterMappings.get(i);
                String parameterMappingName = parameterMapping.getName();
                Object value = null;
                if (param instanceof Map) {
                    value = ((Map) param).get(parameterMappingName);
                } else {
                    Field field = paramClassType.getDeclaredField(parameterMappingName);
                    field.setAccessible(true);
                    value = field.get(param);
                }
                preparedStatement.setObject(i + 1, value);
            }
        }
    }
}
