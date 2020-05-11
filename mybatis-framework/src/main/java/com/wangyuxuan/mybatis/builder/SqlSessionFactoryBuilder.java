package com.wangyuxuan.mybatis.builder;

import com.wangyuxuan.mybatis.config.Configuration;
import com.wangyuxuan.mybatis.factory.DefaultSqlSessionFactory;
import com.wangyuxuan.mybatis.factory.SqlSessionFactory;
import com.wangyuxuan.mybatis.parser.XMLConfigBuilder;
import com.wangyuxuan.mybatis.utils.DocumentUtils;
import org.dom4j.Document;

import java.io.InputStream;
import java.io.Reader;

/**
 * @author wangyuxuan
 * @date 2020/5/11 10:20
 * @description 作用：解析配置文件，封装Configuration，创建SqlSessionFactory
 */
public class SqlSessionFactoryBuilder {

    /**
     * inputStream类型
     *
     * @param inputStream
     * @return
     */
    public SqlSessionFactory build(InputStream inputStream) {
        XMLConfigBuilder configBuilder = new XMLConfigBuilder();
        Document document = DocumentUtils.createDocument(inputStream);
        Configuration configuration = configBuilder.parse(document.getRootElement());
        return build(configuration);
    }

    /**
     * reader类型
     *
     * @param reader
     * @return
     */
    public SqlSessionFactory build(Reader reader) {
        return null;
    }

    private SqlSessionFactory build(Configuration configuration) {
        return new DefaultSqlSessionFactory(configuration);
    }
}
