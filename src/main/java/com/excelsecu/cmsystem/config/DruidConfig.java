package com.excelsecu.cmsystem.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * Druid数据源监控
 * http://localhost:8118/druid/login.html
 * */
@Configuration
public class DruidConfig {

    @ConfigurationProperties(prefix = "spring.datasource")
    @Bean
    public DataSource druidDataSource(){
        return new DruidDataSource();
    }

    @Bean
    public ServletRegistrationBean statViewServlet(){
        ServletRegistrationBean<StatViewServlet> bean = new ServletRegistrationBean<>(new StatViewServlet(), "/druid/*");
        // 后台需要有人登录，账号密码配置
        HashMap<String, String> initParameters = new HashMap<>();
        // 增加配置, Key为固定参数不能修改
        initParameters.put("loginUsername","admin");
        initParameters.put("loginPassword","1q2w3e4r");
        // 允许谁可以访问
        initParameters.put("allow","");
        // 禁止谁能访问
        // initParameters.put("xxxx","192.168.xxx.xxx");
        // 设置初始化参数
        bean.setInitParameters(initParameters);
        return bean;
    }

    @Bean
    public FilterRegistrationBean webStatFilter(){
        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.setFilter(new WebStatFilter());
        Map<String,String> initParameters = new HashMap<>();
        // 过滤器-这些东西不进行统计
        initParameters.put("exclusions","*.js,*.css,/druid/**");
        bean.setInitParameters(initParameters);
        return bean;
    }

}
