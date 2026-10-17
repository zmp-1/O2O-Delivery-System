package com.sky;

import com.sky.properties.AliOssProperties;
import com.sky.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.ExceptionHandler;

@EnableScheduling   //开启任务调度
@SpringBootApplication
@EnableTransactionManagement
@MapperScan("com.sky.mapper")
//开启注解方式的事务管理
@EnableConfigurationProperties(AliOssProperties.class)

@Slf4j
@EnableCaching
//开启我们的缓存注解
public class SkyApplication {
    public static void main(String[] args) {
        SpringApplication.run(SkyApplication.class, args);
        log.info("server started");
    }



}
