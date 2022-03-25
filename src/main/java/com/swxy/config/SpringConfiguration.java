package com.swxy.config;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan("com.swxy.core")
@Import({DataSourceConfig.class})
public class SpringConfiguration {
}
