package com.wangyuxuan.spring.demo1.service;

import com.wangyuxuan.spring.demo1.po.User;

import java.util.List;
import java.util.Map;

/**
 * @author wangyuxuan
 * @date 2020/5/15 10:18
 * @description UserService接口
 */
public interface UserService {

    List<User> queryUsers(Map<String, Object> param);
}
