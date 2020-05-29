package com.wangyuxuan.springmvc.adapter;

import com.wangyuxuan.springmvc.adapter.iface.HandlerAdapter;
import com.wangyuxuan.springmvc.handler.iface.SimpleControllerHandler;
import com.wangyuxuan.springmvc.model.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author wangyuxuan
 * @date 2020/5/29 9:40 下午
 * @description 适配并处理SimpleControllerHandler处理器类的
 */
public class SimpleControllerHandlerAdapter implements HandlerAdapter {

    @Override
    public boolean supports(Object handler) {
        return handler instanceof SimpleControllerHandler;
    }

    @Override
    public ModelAndView handleRequest(Object handler, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return ((SimpleControllerHandler) handler).handleRequest(request, response);
    }
}
