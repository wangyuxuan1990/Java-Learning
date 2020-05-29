package com.wangyuxuan.springmvc.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author wangyuxuan
 * @date 2020/5/29 17:52
 * @description AbstractServlet
 */
public abstract class AbstractServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    /**
     * 请求分发
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected abstract void doDispatch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
}
