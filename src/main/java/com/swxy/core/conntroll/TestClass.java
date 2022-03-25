package com.swxy.core.conntroll;

import com.swxy.config.DataSourceConfig;
import com.swxy.config.SpringConfiguration;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;



public class TestClass {

    public static void main(String[] args) throws Exception {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringConfiguration.class);
        DataSourceConfig con = context.getBean(DataSourceConfig.class);

    }
}
