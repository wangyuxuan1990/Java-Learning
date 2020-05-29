package com.wangyuxuan.springmvc.handler.iface;

import com.wangyuxuan.springmvc.model.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author wangyuxuan
 * @date 2020/5/29 9:11 下午
 * @description 模仿Servlet的处理方式，而且可以在response之前，针对响应结果进行拦截处理
 */
public interface SimpleControllerHandler {

    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception;
}
