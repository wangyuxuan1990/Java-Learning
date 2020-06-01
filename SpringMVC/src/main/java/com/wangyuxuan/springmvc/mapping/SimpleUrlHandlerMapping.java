package com.wangyuxuan.springmvc.mapping;

import com.wangyuxuan.springmvc.handler.SaveUserHandler;
import com.wangyuxuan.springmvc.mapping.iface.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wangyuxuan
 * @date 2020/5/29 9:33 下午
 * @description 将处理器类配置到xml中的bean标签
 */
public class SimpleUrlHandlerMapping implements HandlerMapping {

    /**
     * 请求和处理器类的映射集合
     */
    private Map<String, Object> urlHandlers = new HashMap<>();

    public void init() {
        urlHandlers.put("/saveUser", new SaveUserHandler());
    }

//    public SimpleUrlHandlerMapping() {
//        urlHandlers.put("/saveUser", new SaveUserHandler());
//    }

    @Override
    public Object getHandler(HttpServletRequest request) throws Exception {
        String uri = request.getRequestURI();
        return urlHandlers.get(uri);
    }
}
