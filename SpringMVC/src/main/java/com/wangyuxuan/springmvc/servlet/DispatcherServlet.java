package com.wangyuxuan.springmvc.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author wangyuxuan
 * @date 2020/5/29 17:52
 * @description DispatcherServlet
 */
public class DispatcherServlet extends AbstractServlet {

    @Override
    protected void doDispatch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 根据请求查找处理器

        // 根据处理器查找处理器适配器

        // 请求处理器适配器执行处理器功能
    }
}
