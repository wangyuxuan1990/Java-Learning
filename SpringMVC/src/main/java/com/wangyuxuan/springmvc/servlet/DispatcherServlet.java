package com.wangyuxuan.springmvc.servlet;

import com.wangyuxuan.spring.framework.factory.support.DefaultListableBeanFactory;
import com.wangyuxuan.spring.framework.reader.XmlBeanDefinitionReader;
import com.wangyuxuan.spring.framework.resource.Resource;
import com.wangyuxuan.spring.framework.resource.support.ClasspathResource;
import com.wangyuxuan.springmvc.adapter.iface.HandlerAdapter;
import com.wangyuxuan.springmvc.mapping.iface.HandlerMapping;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wangyuxuan
 * @date 2020/5/29 17:52
 * @description DispatcherServlet
 */
public class DispatcherServlet extends AbstractServlet {

    private List<HandlerMapping> handlerMappings = new ArrayList<>();
    private List<HandlerAdapter> handlerAdapters = new ArrayList<>();

    private DefaultListableBeanFactory beanFactory;

    /**
     * 该方法只会在Servlet初始化的时候，被执行一次
     *
     * @param config
     * @throws ServletException
     */
    @Override
    public void init(ServletConfig config) throws ServletException {
        // 获取web.xml中的springmvc的配置文件地址
        String configLocation = config.getInitParameter("contextConfigLocation");
        // 创建BeanFactory
        initSpringContainer(configLocation);
        initStrategy();
//        handlerMappings.add(new BeanNameUrlHandlerMapping());
//        handlerMappings.add(new SimpleUrlHandlerMapping());
//        handlerAdapters.add(new SimpleControllerHandlerAdapter());
//        handlerAdapters.add(new HttpRequestHandlerAdapter());
    }

    private void initSpringContainer(String configLocation) {
        // 加载SpringMVC配置文件，解析BeanDefinition
        beanFactory = new DefaultListableBeanFactory();

        Resource resource = new ClasspathResource(configLocation);
        InputStream inputStream = resource.getResource();

        XmlBeanDefinitionReader definitionReader = new XmlBeanDefinitionReader(beanFactory);
        definitionReader.registerBeanDefinitions(inputStream);

        // 初始化整个Spring容器中的所有单例bean
        beanFactory.getBeansByType(Object.class);
    }

    private void initStrategy() {
        if (beanFactory == null) {
            return;
        }
        handlerMappings = beanFactory.getBeansByType(HandlerMapping.class);
        handlerAdapters = beanFactory.getBeansByType(HandlerAdapter.class);
    }

    @Override
    protected void doDispatch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // 根据请求查找处理器
            Object handler = getHandler(request);
            if (handler == null) {
                return;
            }
            // 根据处理器查找处理器适配器
            HandlerAdapter ha = getHandlerAdapter(handler);
            if (ha == null) {
                return;
            }
            // 请求处理器适配器执行处理器功能
            ha.handleRequest(handler, request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据处理器，找到对应的处理器适配器
     *
     * @param handler
     * @return
     */
    private HandlerAdapter getHandlerAdapter(Object handler) {
        // 根据处理器对象， 从一堆的处理器适配器中，匹配到合适的处理器适配器
        // 策略模式
        if (handlerAdapters != null) {
            for (HandlerAdapter ha : handlerAdapters) {
                if (ha.supports(handler)) {
                    return ha;
                }
            }
        }

        //如果沒有策略模式
//		HandlerAdapter ha;
//		ha = new HttpRequestHandlerAdapter();
//		if(ha.supports(handler)) {
//			return ha;
//		}
//		ha = new SimpleControllerHandlerAdapter();
//		if(ha.supports(handler)) {
//			return ha;
//		}
        return null;
    }

    /**
     * 根据请求，查找对应的处理器
     *
     * @param request
     * @return
     * @throws Exception
     */
    private Object getHandler(HttpServletRequest request) throws Exception {
        // 需要通过处理器映射器去查找对应的处理器
        if (handlerMappings != null) {
            for (HandlerMapping hm : handlerMappings) {
                Object handler = hm.getHandler(request);
                if (handler != null) {
                    return handler;
                }
            }
        }
        return null;
    }
}
