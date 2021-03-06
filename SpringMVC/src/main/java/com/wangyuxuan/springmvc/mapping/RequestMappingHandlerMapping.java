package com.wangyuxuan.springmvc.mapping;

import com.wangyuxuan.spring.framework.aware.BeanFactoryAware;
import com.wangyuxuan.spring.framework.factory.BeanFactory;
import com.wangyuxuan.spring.framework.factory.support.DefaultListableBeanFactory;
import com.wangyuxuan.spring.framework.ioc.BeanDefinition;
import com.wangyuxuan.springmvc.annotation.Controller;
import com.wangyuxuan.springmvc.annotation.RequestMapping;
import com.wangyuxuan.springmvc.mapping.iface.HandlerMapping;
import com.wangyuxuan.springmvc.model.HandlerMethod;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wangyuxuan
 * @date 2020/6/2 9:56
 * @description 查找注解方式下的处理器类
 */
public class RequestMappingHandlerMapping implements HandlerMapping, BeanFactoryAware {

    private DefaultListableBeanFactory beanFactory;

    /**
     * 请求和处理器类的映射集合
     */
    private Map<String, HandlerMethod> urlHandlers = new HashMap<>();

    /**
     * 初始化方法
     */
    public void init() {
        List<BeanDefinition> beanDefinitions = beanFactory.getBeanDefinitions();
        for (BeanDefinition bd : beanDefinitions) {
            String beanName = bd.getBeanName();
            String clazzName = bd.getClazzName();
            Class<?> type = resolveType(clazzName);
            if (isHandler(type)) {
                Method[] methods = type.getDeclaredMethods();
                for (Method method : methods) {
                    if (method.isAnnotationPresent(RequestMapping.class)) {
                        StringBuffer sb = new StringBuffer();
                        RequestMapping mapping = type.getAnnotation(RequestMapping.class);
                        // 类上的URL "/user"
                        String classUrl = mapping.value();
                        sb.append(classUrl);
                        RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
                        // 方法上的URL "/queryUser"
                        String methodUrl = requestMapping.value();
                        sb.append(methodUrl);
                        // 通过beanName从Spring容器中获取Controller类的实例
                        Object bean = beanFactory.getBean(beanName);
                        HandlerMethod hm = new HandlerMethod(bean, method);
                        urlHandlers.put(sb.toString(), hm);
                    }
                }
            }
        }
    }

    private boolean isHandler(Class<?> type) {
        return type.isAnnotationPresent(Controller.class) || type.isAnnotationPresent(RequestMapping.class);
    }

    private Class<?> resolveType(String clazzName) {
        try {
            return Class.forName(clazzName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Object getHandler(HttpServletRequest request) throws Exception {
        String uri = request.getRequestURI();
        return urlHandlers.get(uri);
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = (DefaultListableBeanFactory) beanFactory;
    }

}
