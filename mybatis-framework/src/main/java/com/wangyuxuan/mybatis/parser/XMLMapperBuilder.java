package com.wangyuxuan.mybatis.parser;

import com.wangyuxuan.mybatis.config.Configuration;
import org.dom4j.Element;

import java.util.List;

/**
 * @author wangyuxuan
 * @date 2020/5/11 11:17
 * @description 解析映射文件
 */
public class XMLMapperBuilder {

    private Configuration configuration;

    public XMLMapperBuilder(Configuration configuration) {
        this.configuration = configuration;
    }

    public void parse(Element rootElement) {
        // TODO 映射文件中，不只是拥有CRUD标签，还有sql片段，还有resultMap等标签的解析
        // 为了方便管理statement，需要使用statement唯一标识
        String namespace = rootElement.attributeValue("namespace");
        List<Element> selectElements = rootElement.elements("select");
        for (Element selectElement : selectElements) {
            XMLStatementBuilder statementBuilder = new XMLStatementBuilder(configuration);
            statementBuilder.parse(selectElement, namespace);
        }
    }
}
