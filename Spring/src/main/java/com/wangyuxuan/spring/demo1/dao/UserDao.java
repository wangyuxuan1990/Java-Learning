package com.wangyuxuan.spring.demo1.dao;

import com.wangyuxuan.spring.demo1.po.User;

import java.util.List;
import java.util.Map;

/**
 * @author wangyuxuan
 * @date 2020/5/15 9:51
 * @description UserDao接口
 */
public interface UserDao {

    List<User> queryUserList(Map<String, Object> param);
}
