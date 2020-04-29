package com.wangyuxuan.mybatis.demo1;

import com.wangyuxuan.mybatis.demo1.po.User;
import com.wangyuxuan.mybatis.demo1.utils.SimpleTypeRegistry;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @author wangyuxuan
 * @date 2020/4/29 9:21 下午
 * @description 解决硬编码问题
 */
public class MybatisV1 {

    private Properties properties = new Properties();

    private static String DRIVER = "db.driver";

    /**
     * 加载properties配置文件
     */
    public void loadProperties() {
        try {
            InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("jdbc.properties");
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 执行JDBC代码，完成查询用户操作
     *
     * @param statementId
     * @param paramObject
     * @return
     */
    public List<Object> selectList(String statementId, Object paramObject) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        // 定义返回的集合
        List<Object> results = new ArrayList<>();
        try {
            // 加载数据库驱动
            Class.forName(properties.getProperty(DRIVER));
            // 获取数据库链接
            connection = DriverManager.getConnection(properties.getProperty("db.url"), properties.getProperty("db.username"), properties.getProperty("db.password"));
            // 定义SQL语句
            String sql = properties.getProperty("db.sql." + statementId);
            // 获取statement
            preparedStatement = connection.prepareStatement(sql);
            // 先获取参数类型
            String parameterType = properties.getProperty("db.sql." + statementId + ".parametertype");
            Class<?> parameterTypeClass = Class.forName(parameterType);
            // 判断输入参数类型
            if (SimpleTypeRegistry.isSimpleType(parameterTypeClass)) {
                preparedStatement.setObject(1, paramObject);
            } else {
                // 遍历SQL语句当中的参数列表
                String params = properties.getProperty("db.sql." + statementId + ".params");
                String[] paramArray = params.split(",");
                for (int i = 0; i < paramArray.length; i++) {
                    // 根据列名获取入参对象的属性值，前提：列名和属性名称要一致
                    Field field = parameterTypeClass.getDeclaredField(paramArray[i]);
                    field.setAccessible(true);
                    // 获取属性值
                    Object value = field.get(paramObject);
                    preparedStatement.setObject(i + 1, value);
                }
            }
            // 执行SQL 获取结果集
            resultSet = preparedStatement.executeQuery();
            // 获取要映射的结果类型
            String resultclassname = properties.getProperty("db.sql." + statementId + ".resultclassname");
            Class<?> resultTypeClass = Class.forName(resultclassname);
            Object result = null;
            while (resultSet.next()) {
                // 每一行对应一个对象
                result = resultTypeClass.newInstance();
                ResultSetMetaData metaData = resultSet.getMetaData();
                int columnCount = metaData.getColumnCount();
                for (int i = 1; i <= columnCount; i++) {
                    // 获取结果集中的列名
                    String columnName = metaData.getColumnName(i);
                    Field field = resultTypeClass.getDeclaredField(columnName);
                    field.setAccessible(true);
                    field.set(result, resultSet.getObject(columnName));
                }
                results.add(result);
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
        return results;
    }

    @Test
    public void test() {
        loadProperties();
        User user = new User();
        user.setUsername("wangyuxuan");
        List<Object> list = selectList("queryUserById", user);
        System.out.println(list);
    }
}
