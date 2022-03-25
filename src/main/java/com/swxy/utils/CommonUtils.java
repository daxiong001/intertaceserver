package com.swxy.utils;

public class CommonUtils {


    public static Object getServicesProxyInstance(Object obj){
        LogProxyFactory proxyFactory = new LogProxyFactory();
        return proxyFactory.createProxy(obj);
    }
}
