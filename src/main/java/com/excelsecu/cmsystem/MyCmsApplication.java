package com.excelsecu.cmsystem;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan("com.excelsecu.cmsystem.mapper")
@ComponentScan("com.excelsecu")
public class MyCmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyCmsApplication.class, args);
    }

}
