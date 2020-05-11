package com.wangyuxuan.mybatis.sqlsession;

import java.util.List;

/**
 * @author wangyuxuan
 * @date 2020/5/10 9:39 下午
 * @description SqlSession接口
 */
public interface SqlSession {

    /**
     * 查询单条记录
     *
     * @param statementId
     * @param param
     * @param <T>
     * @return
     */
    <T> T selectOne(String statementId, Object param);

    /**
     * 查询多条记录
     *
     * @param statementId
     * @param param
     * @param <T>
     * @return
     */
    <T> List<T> selectList(String statementId, Object param);
}
