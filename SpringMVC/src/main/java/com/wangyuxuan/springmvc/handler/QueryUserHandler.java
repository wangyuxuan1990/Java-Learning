package com.wangyuxuan.springmvc.handler;

import com.wangyuxuan.springmvc.handler.iface.HttpRequestHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author wangyuxuan
 * @date 2020/5/29 9:18 下午
 * @description 处理查询用户请求
 */
public class QueryUserHandler implements HttpRequestHandler {
    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("text/plain;charset=utf8");
        response.getWriter().write("QueryUserHandler...");
    }
}
