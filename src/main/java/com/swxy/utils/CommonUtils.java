package com.swxy.utils;

import com.swxy.common.ExcelMeta;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class CommonUtils {


    /**
     * 复制一个二维数组
     * @param objects
     * @return
     */
    public static Object[][] copyArrays(Object[][] objects){
        int one = 0;
        if (objects!=null){
            for (int i = 0; i < objects.length; i++) {
                if (objects[i].length == 0 || objects[i][0] == null){
                    continue;
                }
                one++;
            }
        }
        Object[][] copy = new Object[one][1];
        for (int i = 0; i < copy.length; i++) {
            System.arraycopy(objects[i], 0, copy[i], 0, 1);
        }
        return copy;
    }

    /**
     * 获取excel数据以map形式存放到list中
     * @param className
     * @return
     */
    public static List<Map<String,String>> getExcelData(String className){
        List<Map<String,String>> lists = new ArrayList<>();
        DataProviders data = new DataProviders(className);
        while (data.hasNext()) {
            lists.add(data.next());
        }
        return lists;
    }


    /**
     * 把excel获取的test数据转化成testng的数据
     * @param className
     * @param method
     * @return
     */
    public static Object[][] getTestNGData(String className, Method method){
        Object[][] result = new Object[1024][1];
        final int[] i = {0};
        List<Map<String,String>> arrayList;
        arrayList = CommonUtils.getExcelData(className);
        arrayList.forEach(stringStringMap -> stringStringMap.forEach((s, s2) -> {
            if (s.equals(ExcelMeta.FUNCTIONNAME.getName())&&s2.equals(method.getName())){
                result[i[0]][0] = stringStringMap.get(ExcelMeta.INPUTJSON.getName());
                i[0]++;
            }
        }));
        return CommonUtils.copyArrays(result);
    }

    /**
     * 获取services层工厂代理对象
     * @param obj
     * @return
     */
    public static Object getServicesProxyInstance(Object obj){
        LogProxyFactory proxyFactory = new LogProxyFactory();
        return proxyFactory.createProxy(obj);
    }
}
