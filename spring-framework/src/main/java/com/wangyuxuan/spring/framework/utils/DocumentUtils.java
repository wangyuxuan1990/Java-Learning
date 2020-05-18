package com.wangyuxuan.spring.framework.utils;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

import java.io.InputStream;

/**
 * @author wangyuxuan
 * @date 2020/5/18 11:01
 * @description Document工具类
 */
public class DocumentUtils {

    public static Document creatDocument(InputStream inputStream) {
        Document document = null;
        try {
            SAXReader saxReader = new SAXReader();
            document = saxReader.read(inputStream);
            return document;
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return null;
    }
}
