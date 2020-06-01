package com.wangyuxuan.springmvc.mapping;

import com.wangyuxuan.spring.framework.aware.BeanFactoryAware;
import com.wangyuxuan.spring.framework.factory.BeanFactory;
import com.wangyuxuan.spring.framework.factory.support.DefaultListableBeanFactory;
import com.wangyuxuan.spring.framework.ioc.BeanDefinition;
import com.wangyuxuan.springmvc.mapping.iface.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wangyuxuan
 * @date 2020/5/29 9:32 下午
 * @description 将处理器类配置到xml中的bean标签
 */
public class BeanNameUrlHandlerMapping implements HandlerMapping, BeanFactoryAware {

    private DefaultListableBeanFactory beanFactory;

    /**
     * 请求和处理器类的映射集合
     */
    private Map<String, Object> urlHandlers = new HashMap<>();

    /**
     * 初始化方法
     */
    public void init() {
        List<BeanDefinition> beanDefinitions = beanFactory.getBeanDefinitions();
        for (BeanDefinition bd : beanDefinitions) {
            String beanName = bd.getBeanName();
            if (beanName == null) {
                continue;
            }
            if (beanName.startsWith("/")) {
                urlHandlers.put(beanName, beanFactory.getBean(beanName));
            }
        }
    }

//    public BeanNameUrlHandlerMapping() {
//        urlHandlers.put("/queryUser", new QueryUserHandler());
//    }

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
