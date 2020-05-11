package com.wangyuxuan.mybatis.parser;

import com.wangyuxuan.mybatis.config.Configuration;
import com.wangyuxuan.mybatis.config.MappedStatement;
import com.wangyuxuan.mybatis.sqlsource.iface.SqlSource;
import com.wangyuxuan.mybatis.utils.ReflectUtils;
import org.dom4j.Element;

/**
 * @author wangyuxuan
 * @date 2020/5/11 11:23
 * @description 专门用来解析CRUD标签的
 */
public class XMLStatementBuilder {

    private Configuration configuration;

    public XMLStatementBuilder(Configuration configuration) {
        this.configuration = configuration;
    }

    public void parse(Element selectElement, String namespace) {
        String statementId = selectElement.attributeValue("id");
        if (statementId == null || statementId.equals("")) {
            return;
        }
        // 一个CURD标签对应一个MappedStatement对象
        // 一个MappedStatement对象由一个statementId来标识，所以保证唯一性
        // statementId = namespace + "." + CRUD标签的id属性
        statementId = namespace + "." + statementId;
        String parameterType = selectElement.attributeValue("parameterType");
        Class<?> parameterTypeClass = ReflectUtils.resolveType(parameterType);
        String resultType = selectElement.attributeValue("resultType");
        Class<?> resultTypeClass = ReflectUtils.resolveType(resultType);
        String statementType = selectElement.attributeValue("statementType");
        statementType = statementType == null || statementType.equals("") ? "prepared" : statementType;
        // SqlSource的封装过程
        SqlSource sqlSource = createSqlSource(selectElement);
        // TODO 建议使用构建者模式去优化
        MappedStatement mappedStatement = new MappedStatement(statementId, sqlSource, statementType, parameterTypeClass, resultTypeClass);
        configuration.addMappedStatement(statementId, mappedStatement);
    }

    private SqlSource createSqlSource(Element selectElement) {
        XMLScriptBuilder scriptBuilder = new XMLScriptBuilder();
        SqlSource sqlSource = scriptBuilder.parseScriptNode(selectElement);
        return sqlSource;
    }
}
