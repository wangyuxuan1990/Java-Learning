package com.wangyuxuan.mybatis.parser;

import com.wangyuxuan.mybatis.config.Configuration;
import com.wangyuxuan.mybatis.io.Resources;
import com.wangyuxuan.mybatis.utils.DocumentUtils;
import org.apache.commons.dbcp.BasicDataSource;
import org.dom4j.Document;
import org.dom4j.Element;

import java.io.InputStream;
import java.util.List;
import java.util.Properties;

/**
 * @author wangyuxuan
 * @date 2020/5/11 11:00
 * @description 主要是用来解析全局配置文件的
 */
public class XMLConfigBuilder {

    private Configuration configuration;

    public XMLConfigBuilder() {
        this.configuration = new Configuration();
    }

    public Configuration parse(Element rootElement) {
        Element envirsElement = rootElement.element("environments");
        parseEnvironments(envirsElement);
        Element mappersElement = rootElement.element("mappers");
        parseMappers(mappersElement);
        return configuration;
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

    private void parseMappers(Element mappers) {
        List<Element> mapperElements = mappers.elements("mapper");
        for (Element mapperElement : mapperElements) {
            String resource = mapperElement.attributeValue("resource");
            InputStream inputStream = Resources.getResourceAsStream(resource);
            Document document = DocumentUtils.createDocument(inputStream);
            // 按照映射文件的语义进行解析
            XMLMapperBuilder mapperBuilder = new XMLMapperBuilder(configuration);
            mapperBuilder.parse(document.getRootElement());
        }
    }
}
