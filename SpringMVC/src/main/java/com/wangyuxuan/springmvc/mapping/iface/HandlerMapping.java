package com.wangyuxuan.springmvc.mapping.iface;

import javax.servlet.http.HttpServletRequest;

/**
 * @author wangyuxuan
 * @date 2020/5/29 9:26 下午
 * @description 处理器映射器
 */
public interface HandlerMapping {

    Object getHandler(HttpServletRequest request) throws Exception;
}
