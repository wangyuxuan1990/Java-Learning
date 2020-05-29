package com.wangyuxuan.springmvc.handler;

import com.wangyuxuan.springmvc.handler.iface.SimpleControllerHandler;
import com.wangyuxuan.springmvc.model.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author wangyuxuan
 * @date 2020/5/29 9:22 下午
 * @description 处理添加用户的请求
 */
public class SaveUserHandler implements SimpleControllerHandler {
    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("text/plain;charset=utf8");
        response.getWriter().write("SaveUserHandler...");
        return null;
    }
}
