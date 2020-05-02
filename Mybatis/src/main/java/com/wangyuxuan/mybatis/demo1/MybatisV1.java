package com.wangyuxuan.mybatis.demo1;

import com.wangyuxuan.mybatis.demo1.po.User;
import com.wangyuxuan.mybatis.demo1.utils.SimpleTypeRegistry;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.*;

/**
 * @author wangyuxuan
 * @date 2020/4/29 9:21 下午
 * @description 解决硬编码问题
 */
public class MybatisV1 {

    private Properties properties = new Properties();

    private static final String DRIVER = "db.driver";

    @Test
    public void test() {
        loadProperties();
        Map<String, Object> param1 = new HashMap<>();
        param1.put("username", "wangyuxuan");
        param1.put("sex", 1);
        User param2 = new User();
        param2.setUsername("wangyuxuan");
        param2.setSex("1");
        List<User> result1 = selectList("queryUserById", param1);
        List<User> result2 = selectList("queryUserById", param2);
        System.out.println(result1);
        System.out.println(result2);
    }

    private void loadProperties() {
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("jdbc.properties");
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private <T> List<T> selectList(String statementId, Object param) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<T> resultList = new ArrayList<>();
        try {
            // 加载数据库驱动
            Class.forName(properties.getProperty(DRIVER));
            // 获取数据库链接
            connection = DriverManager.getConnection(properties.getProperty("db.url"), properties.getProperty("db.username"), properties.getProperty("db.password"));
            // 定义SQL语句
            String sql = properties.getProperty("db.sql." + statementId);
            // 获取statement
            preparedStatement = connection.prepareStatement(sql);
            Class<?> paramClassType = param.getClass();
            // 设置参数
            if (SimpleTypeRegistry.isSimpleType(paramClassType)) {
                preparedStatement.setObject(1, param);
            } else {
                String params = properties.getProperty("db.sql." + statementId + ".params");
                String[] paramsArray = params.split(",");
                for (int i = 0; i < paramsArray.length; i++) {
                    Object value = null;
                    if (param instanceof Map) {
                        value = ((Map) param).get(paramsArray[i]);
                    } else {
                        Field field = paramClassType.getDeclaredField(paramsArray[i]);
                        field.setAccessible(true);
                        value = field.get(param);
                    }
                    preparedStatement.setObject(i + 1, value);
                }
            }
            // 执行SQL 获取结果集
            resultSet = preparedStatement.executeQuery();
            String resultClassName = properties.getProperty("db.sql." + statementId + ".resultclassname");
            Class<?> resultClassType = Class.forName(resultClassName);
            // 遍历输出查询结果集
            while (resultSet.next()) {
                Object result = resultClassType.newInstance();
                ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
                int columnCount = resultSetMetaData.getColumnCount();
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = resultSetMetaData.getColumnName(i);
                    Field field = resultClassType.getDeclaredField(columnName);
                    field.setAccessible(true);
                    field.set(result, resultSet.getObject(columnName));
                }
                resultList.add((T) result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 释放资源
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            // 释放资源
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            // 释放资源
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return resultList;
    }
}
