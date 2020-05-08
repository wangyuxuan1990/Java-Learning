package com.wangyuxuan.mybatis.demo1;

import com.wangyuxuan.mybatis.demo1.framework.config.Configuration;
import com.wangyuxuan.mybatis.demo1.framework.config.MappedStatement;
import com.wangyuxuan.mybatis.demo1.framework.sqlnode.IfSqlNode;
import com.wangyuxuan.mybatis.demo1.framework.sqlnode.MixedSqlNode;
import com.wangyuxuan.mybatis.demo1.framework.sqlnode.StaticTextSqlNode;
import com.wangyuxuan.mybatis.demo1.framework.sqlnode.TextSqlNode;
import com.wangyuxuan.mybatis.demo1.framework.sqlnode.iface.SqlNode;
import com.wangyuxuan.mybatis.demo1.framework.sqlsource.BoundSql;
import com.wangyuxuan.mybatis.demo1.framework.sqlsource.DynamicSqlSource;
import com.wangyuxuan.mybatis.demo1.framework.sqlsource.ParameterMapping;
import com.wangyuxuan.mybatis.demo1.framework.sqlsource.RawSqlSource;
import com.wangyuxuan.mybatis.demo1.framework.sqlsource.iface.SqlSource;
import com.wangyuxuan.mybatis.demo1.po.User;
import com.wangyuxuan.mybatis.demo1.utils.SimpleTypeRegistry;
import org.apache.commons.dbcp.BasicDataSource;
import org.dom4j.*;
import org.dom4j.io.SAXReader;
import org.junit.Test;

import javax.sql.DataSource;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.*;

/**
 * @author wangyuxuan
 * @date 2020/5/2 3:13 下午
 * @description 1.properties配置文件升级为XML配置文件 2.使用面向过程思维去优化代码
 */
public class MybatisV2 {

    private Configuration configuration = new Configuration();

    private String namespace;
    // 如果包含${}或者动态标签，那么isDynamic为true
    private boolean isDynamic = false;

    @Test
    public void test() {
        loadConfiguration();
        Map<String, Object> param = new HashMap<>();
        param.put("username", "wangyuxuan");
        param.put("id", 1);
        List<User> users = selectList("test.findUserById", param);
        System.out.println(users);
    }

    private <T> List<T> selectList(String statementId, Object param) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<T> resultList = new ArrayList<>();
        try {
            // 获取数据库链接
            connection = getConnection();
            // 先要获取MappedStatement
            MappedStatement mappedStatement = configuration.getMappedStatementById(statementId);
            // 再获取MappedStatement中的SqlSource
            SqlSource sqlSource = mappedStatement.getSqlSource();
            // 通过SqlSource的API处理，获取BoundSql
            BoundSql boundSql = sqlSource.getBoundSql(param);
            // 通过BoundSql获取到SQL语句
            String sql = boundSql.getSql();
            // 获取statement
            preparedStatement = connection.prepareStatement(sql);
            // 参数处理
            setParameters(preparedStatement, param, boundSql);
            // 执行SQL 获取结果集
            resultSet = preparedStatement.executeQuery();
            // 遍历查询结果集
            handleResultSet(resultSet, resultList, mappedStatement);
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

    private <T> void handleResultSet(ResultSet resultSet, List<T> resultList, MappedStatement mappedStatement) throws Exception {
        Class<?> resultClassType = mappedStatement.getResultTypeClass();
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
    }

    private void setParameters(PreparedStatement preparedStatement, Object param, BoundSql boundSql) throws Exception {
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

    /**
     * 通过数据源获取连接
     *
     * @return
     */
    private Connection getConnection() {
        DataSource dataSource = configuration.getDataSource();
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return connection;
    }

    /**
     * 加载XML文件
     */
    private void loadConfiguration() {
        String location = "mybatis-config.xml";
        InputStream inputStream = getResourceAsStream(location);
        Document document = createDocument(inputStream);
        // 按照mybatis的语义，对XML中的内容进行解析
        parseConfiguration(document.getRootElement());
    }

    private void parseConfiguration(Element rootElement) {
        Element envirsElement = rootElement.element("environments");
        parseEnvironments(envirsElement);
        Element mappersElement = rootElement.element("mappers");
        parseMappers(mappersElement);
    }

    private void parseMappers(Element mappers) {
        List<Element> mapperElements = mappers.elements("mapper");
        for (Element mapperElement : mapperElements) {
            parseMapper(mapperElement);
        }
    }

    private void parseMapper(Element mapperElement) {
        String resource = mapperElement.attributeValue("resource");
        InputStream inputStream = getResourceAsStream(resource);
        Document document = createDocument(inputStream);
        // 按照映射文件的语义进行解析
        parseXMLMapper(document.getRootElement());
    }

    private void parseXMLMapper(Element rootElement) {
        // 为了方便管理statement，需要使用statement唯一标识
        namespace = rootElement.attributeValue("namespace");
        List<Element> selectElements = rootElement.elements("select");
        for (Element selectElement : selectElements) {
            parseStatementElement(selectElement);
        }
    }

    private void parseStatementElement(Element selectElement) {
        String statementId = selectElement.attributeValue("id");
        if (statementId == null || statementId.equals("")) {
            return;
        }
        // 一个CURD标签对应一个MappedStatement对象
        // 一个MappedStatement对象由一个statementId来标识，所以保证唯一性
        // statementId = namespace + "." + CRUD标签的id属性
        statementId = namespace + "." + statementId;
        String parameterType = selectElement.attributeValue("parameterType");
        Class<?> parameterTypeClass = resolveType(parameterType);
        String resultType = selectElement.attributeValue("resultType");
        Class<?> resultTypeClass = resolveType(resultType);
        String statementType = selectElement.attributeValue("statementType");
        statementType = statementType == null || statementType.equals("") ? "prepared" : statementType;
        // SqlSource的封装过程
        SqlSource sqlSource = createSqlSource(selectElement);
        // TODO 建议使用构建者模式去优化
        MappedStatement mappedStatement = new MappedStatement(statementId, sqlSource, statementType, parameterTypeClass, resultTypeClass);
        configuration.addMappedStatement(statementId, mappedStatement);
    }

    private SqlSource createSqlSource(Element selectElement) {
        SqlSource sqlSource = parseScriptNode(selectElement);
        return sqlSource;
    }

    /**
     * 处理select等标签下的SQL脚本
     *
     * @param selectElement
     * @return
     */
    private SqlSource parseScriptNode(Element selectElement) {
        // 解析SQL脚本信息
        MixedSqlNode rootSqlNode = parseDynamicTags(selectElement);
        // 将sql脚本信息需要封装SqlSource对象
        SqlSource sqlSource = null;
        // 封装成哪个SqlSource 解析过程中确定类型 因此采用全局变量
        if (isDynamic) {
            sqlSource = new DynamicSqlSource(rootSqlNode);
        } else {
            sqlSource = new RawSqlSource(rootSqlNode);
        }
        return sqlSource;
    }

    private MixedSqlNode parseDynamicTags(Element selectElement) {
        List<SqlNode> sqlNodes = new ArrayList<>();
        // 获取select标签的子节点总数
        int nodeCount = selectElement.nodeCount();
        // 遍历所有子节点
        for (int i = 0; i < nodeCount; i++) {
            Node node = selectElement.node(i);
            // 判断子节点是文本节点还是元素节点
            if (node instanceof Text) {
                // 获取文本信息
                String sqlText = node.getText();
                if (sqlText == null || "".equals(sqlText)) {
                    continue;
                }
                // 将sql文本封装到TextSqlNode
                TextSqlNode textSqlNode = new TextSqlNode(sqlText);
                // 如果包含${}
                if (textSqlNode.isDynamic()) {
                    sqlNodes.add(textSqlNode);
                    isDynamic = true;
                } else {
                    sqlNodes.add(new StaticTextSqlNode(sqlText));
                }
            } else if (node instanceof Element) {
                Element element = (Element) node;
                String elementName = element.getName();
                if (elementName.equals("if")) {
                    String test = element.attributeValue("test");
                    // 递归封装SqlNode数据
                    MixedSqlNode mixedSqlNode = parseDynamicTags(element);
                    // 封装IfSqlNode数据
                    IfSqlNode ifSqlNode = new IfSqlNode(test, mixedSqlNode);
                    sqlNodes.add(ifSqlNode);
                } else if (elementName.equals("where")) {
                    // ...
                }
                isDynamic = true;
            }
        }
        return new MixedSqlNode(sqlNodes);
    }

    private Class<?> resolveType(String type) {
        Class<?> clazz = null;
        try {
            clazz = Class.forName(type);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return clazz;
    }

    private void parseEnvironments(Element envirsElement) {
        String defaultId = envirsElement.attributeValue("default");
        List<Element> envElements = envirsElement.elements("environment");
        for (Element envElement : envElements) {
            String id = envElement.attributeValue("id");
            if (id.equals(defaultId)) {
                parseDataSource(envElement);
            }
        }
    }

    private void parseDataSource(Element envElement) {
        Element dataSourceElement = envElement.element("dataSource");
        String type = dataSourceElement.attributeValue("type");
        if (type.equals("DBCP")) {
            BasicDataSource dataSource = new BasicDataSource();
            Properties properties = parseProperty(dataSourceElement);
            dataSource.setDriverClassName(properties.getProperty("driver"));
            dataSource.setUrl(properties.getProperty("url"));
            dataSource.setUsername(properties.getProperty("username"));
            dataSource.setPassword(properties.getProperty("password"));
            configuration.setDataSource(dataSource);
        }
    }

    private Properties parseProperty(Element dataSourceElement) {
        Properties properties = new Properties();
        List<Element> propElements = dataSourceElement.elements("property");
        for (Element propElement : propElements) {
            String name = propElement.attributeValue("name");
            String value = propElement.attributeValue("value");
            properties.put(name, value);
        }
        return properties;
    }

    private Document createDocument(InputStream inputStream) {
        SAXReader saxReader = new SAXReader();
        Document document = null;
        try {
            document = saxReader.read(inputStream);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return document;
    }

    private InputStream getResourceAsStream(String location) {
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(location);
        return inputStream;
    }
}
