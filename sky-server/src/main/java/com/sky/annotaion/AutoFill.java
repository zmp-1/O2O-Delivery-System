package com.sky.annotaion;

import com.sky.enumeration.OperationType;
import org.springframework.web.bind.annotation.RequestBody;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义注解，用于标识某个方法需要进行功能字段自动填充处理
 */
@Target(ElementType.METHOD)  //表示这个注解用在方法上面
@Retention(RetentionPolicy.RUNTIME)    //运行时保留，aop能读到
public @interface AutoFill {
    //数据库操作类型  update,insert
    OperationType value();
}
