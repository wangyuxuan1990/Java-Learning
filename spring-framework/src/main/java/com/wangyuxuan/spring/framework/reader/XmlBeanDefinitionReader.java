package com.wangyuxuan.spring.framework.reader;

import com.wangyuxuan.spring.framework.registry.BeanDefinitionRegistry;
import com.wangyuxuan.spring.framework.utils.DocumentUtils;
import org.dom4j.Document;

import java.io.InputStream;

/**
 * @author wangyuxuan
 * @date 2020/5/18 10:55
 * @description BeanDefinition阅读器 专门提供针对XML文件，解析并注册BeanDefinition的功能
 */
public class XmlBeanDefinitionReader {

    private BeanDefinitionRegistry beanDefinitionRegistry;

    public XmlBeanDefinitionReader(BeanDefinitionRegistry beanDefinitionRegistry) {
        this.beanDefinitionRegistry = beanDefinitionRegistry;
    }

    public void registerBeanDefinitions(InputStream inputStream) {
        // 创建文档对象
        Document document = DocumentUtils.creatDocument(inputStream);
        XmlBeanDefinitionDocumentReader documentReader = new XmlBeanDefinitionDocumentReader(beanDefinitionRegistry);
        documentReader.loadBeanDefinitions(document.getRootElement());
    }
}
