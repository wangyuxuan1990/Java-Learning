package com.wangyuxuan.mybatis.handler.iface;

import com.wangyuxuan.mybatis.config.MappedStatement;

import java.sql.ResultSet;
import java.util.List;

/**
 * @author wangyuxuan
 * @date 2020/5/11 14:56
 * @description 处理ResultSet逻辑
 */
public interface ResultSetHandler {

    List<Object> handleResultSets(MappedStatement mappedStatement, ResultSet rs) throws Exception;
}
