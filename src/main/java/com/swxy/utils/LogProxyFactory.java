package com.swxy.utils;

import com.swxy.common.CaseMeta;
import com.swxy.common.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * 实现spring中MethodInterceptor方法进行cglib动态代理
 */

@Slf4j
public class LogProxyFactory implements MethodInterceptor {

    //要代理的真实对象
    private Object obj;


    public Object createProxy(Object target){
        this.obj = target;
        Enhancer enhancer = new Enhancer();
        //设置代理目标
        enhancer.setSuperclass(this.obj.getClass());
        //设置单一会调对象，在调用中拦截对目标方法的调用
        enhancer.setCallback(this);
        enhancer.setClassLoader(this.obj.getClass().getClassLoader());
        return enhancer.create();
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {

        if(method.isAnnotationPresent(CaseMeta.class)){
            CaseMeta annotation = method.getAnnotation(CaseMeta.class);
            log.info("----测试用例：<" + annotation + "> 开始执行----");
        }
        log.info("----测试用例逻辑类：<" + o.getClass() + "> ----");
        log.info("----测试用例逻辑方法：<" + method.getName() + "> ----");
        Arrays.stream(objects).forEach(o1 -> log.info("----请求时机入参：<" + o1.toString() + "> ----"));
        Object invokeSuper = methodProxy.invokeSuper(o, objects);
        if (invokeSuper != null){
            if (invokeSuper.getClass().isAssignableFrom(Response.class)){
                log.info("---- 请求实际返回结果 : " + ((Response) invokeSuper).getJsonString() + " ----");
            }else {
                log.info("---- 请求实际返回结果 : " + invokeSuper.toString() + " ----");
            }
        }else {
            log.info("---- 请求实际返回结果为空  : \n{\n} ----");
        }
        return invokeSuper;
    }
}
