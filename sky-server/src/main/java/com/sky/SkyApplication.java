package com.sky;

import com.sky.properties.AliOssProperties;
import com.sky.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.ExceptionHandler;


@SpringBootApplication
@EnableTransactionManagement

//开启注解方式的事务管理



@EnableConfigurationProperties(AliOssProperties.class)
@Slf4j
public class SkyApplication {
    public static void main(String[] args) {
        SpringApplication.run(SkyApplication.class, args);
        log.info("server started");
    }



}
