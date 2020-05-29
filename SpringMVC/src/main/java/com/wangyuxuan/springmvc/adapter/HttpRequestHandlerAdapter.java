package com.wangyuxuan.springmvc.adapter;

import com.wangyuxuan.springmvc.adapter.iface.HandlerAdapter;
import com.wangyuxuan.springmvc.handler.iface.HttpRequestHandler;
import com.wangyuxuan.springmvc.model.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author wangyuxuan
 * @date 2020/5/29 9:42 下午
 * @description 适配并处理HttpRequestHandler处理器类的
 */
public class HttpRequestHandlerAdapter implements HandlerAdapter {

    @Override
    public boolean supports(Object handler) {
        return handler instanceof HttpRequestHandler;
    }

    @Override
    public ModelAndView handleRequest(Object handler, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ((HttpRequestHandler) handler).handleRequest(request, response);
        return null;
    }
}
