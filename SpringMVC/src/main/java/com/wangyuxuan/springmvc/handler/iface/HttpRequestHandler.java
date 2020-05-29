package com.wangyuxuan.springmvc.handler.iface;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author wangyuxuan
 * @date 2020/5/29 9:15 下午
 * @description 模仿Servlet的处理方式
 */
public interface HttpRequestHandler {

    public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception;
}
