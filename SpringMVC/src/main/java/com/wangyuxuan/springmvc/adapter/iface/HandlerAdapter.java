package com.wangyuxuan.springmvc.adapter.iface;

import com.wangyuxuan.springmvc.model.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author wangyuxuan
 * @date 2020/5/29 9:35 下午
 * @description 处理器适配器
 */
public interface HandlerAdapter {

    /**
     * 完成处理器类和HandlerAdapter之间的适配
     *
     * @param handler
     * @return
     */
    boolean supports(Object handler);

    /**
     * 适配器的执行功能（不同的适配器，执行的处理器是不同的）
     *
     * @param handler
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    ModelAndView handleRequest(Object handler, HttpServletRequest request, HttpServletResponse response) throws Exception;
}
