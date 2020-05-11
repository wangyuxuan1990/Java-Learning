package com.wangyuxuan.mybatis.factory;

import com.wangyuxuan.mybatis.sqlsession.SqlSession;

/**
 * @author wangyuxuan
 * @date 2020/5/11 10:12
 * @description 主要用来生产SqlSession
 */
public interface SqlSessionFactory {

    SqlSession openSession();
}
